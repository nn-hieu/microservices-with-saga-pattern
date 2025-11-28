package com.hieunn.commonlib.configs.rabbitmqs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hieunn.commonlib.entities.ProcessedSagaEventBase;
import com.hieunn.commonlib.entities.SagaEventBase;
import com.hieunn.commonlib.repositories.ProcessedSagaEventBaseRepository;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.amqp.core.Message;

import java.io.IOException;
import java.util.Arrays;

@RequiredArgsConstructor
@Slf4j
public class IdempotentInterceptor<T extends ProcessedSagaEventBase> implements MethodInterceptor {
    private final ProcessedSagaEventBaseRepository<T> repository;
    private final ObjectMapper objectMapper;
    private final Class<? extends SagaEventBase> eventClass;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Message message = this.extractMessage(invocation.getArguments());

        @SuppressWarnings("resource")
        Channel channel = this.extractChannel(invocation.getArguments());

        if (message == null || channel == null) {
            log.error("Lack of Message or Channel in method arguments");
            throw new RuntimeException("Lack of Message or Channel in method arguments");
        }

        SagaEventBase event;
        try {
            event = objectMapper.readValue(message.getBody(), eventClass);
        } catch (IOException e) {
            log.error("Failed to parse message: {}", e.getMessage());
            throw e;
        }

        boolean isExisted = repository.existsBySagaIdAndEventName(event.getSagaId(), event.getEventName());
        if (isExisted) {
            log.info("Event: {} with saga id: {} already processed", event.getEventName(), event.getSagaId());

            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            return null;
        }

        try {
            Object result = invocation.proceed();
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

            return result;
        } catch (Throwable t) {
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            throw t;
        }
    }

    private Message extractMessage(Object[] args) {
        return Arrays.stream(args)
                .filter(a -> a instanceof Message)
                .map(Message.class::cast)
                .findFirst().orElse(null);
    }

    private Channel extractChannel(Object[] args) {
        return Arrays.stream(args)
                .filter(arg -> arg instanceof Channel)
                .map(Channel.class::cast)
                .findFirst()
                .orElse(null);
    }
}
