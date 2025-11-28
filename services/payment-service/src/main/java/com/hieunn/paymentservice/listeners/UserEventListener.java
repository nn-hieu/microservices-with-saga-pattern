package com.hieunn.paymentservice.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hieunn.commonlib.dtos.events.SagaEventDto;
import com.hieunn.paymentservice.enums.RabbitMQQueue;
import com.hieunn.paymentservice.handlers.UserEventHandler;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserEventListener {
    private final UserEventHandler userEventHandler;

    @RabbitListener(queues = RabbitMQQueue.USER_DEBIT_SUCCEEDED_QUEUE_NAME)
    public void listenUserDebitSucceededEvent(
            SagaEventDto event,
            @SuppressWarnings("unused") Message message,
            @SuppressWarnings("unused") Channel channel
    ) throws JsonProcessingException {
        log.info("Received user debit succeeded event with payload: {}", event.getPayload());

        userEventHandler.handleUserDebitSucceededEvent(event);
    }

    @RabbitListener(queues = RabbitMQQueue.USER_DEBIT_FAILED_QUEUE_NAME)
    public void listenUserDebitFailedEvent(
            SagaEventDto event,
            @SuppressWarnings("unused") Message message,
            @SuppressWarnings("unused") Channel channel
    ) throws JsonProcessingException {
        log.info("Received user debit failed event with payload: {}", event.getPayload());

        userEventHandler.handleUserDebitFailedEvent(event);
    }
}
