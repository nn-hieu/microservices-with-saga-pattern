package com.hieunn.orderservice.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hieunn.commonlib.dtos.events.SagaEventDto;
import com.hieunn.orderservice.enums.RabbitMQQueue;
import com.hieunn.orderservice.handlers.UserEventHandler;
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