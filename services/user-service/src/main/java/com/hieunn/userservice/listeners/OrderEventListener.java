package com.hieunn.userservice.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hieunn.userservice.entities.SagaEvent;
import com.hieunn.userservice.enums.RabbitMQQueue;
import com.hieunn.userservice.handlers.OrderEventHandler;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventListener {
    private final OrderEventHandler orderEventHandler;

    @RabbitListener(queues = RabbitMQQueue.ORDER_CREATED_SUCCEEDED_QUEUE_NAME)
    public void listenOrderCreatedSucceededEvent(
            SagaEvent event,
            @SuppressWarnings("unused") Message message,
            @SuppressWarnings("unused") Channel channel
    ) throws JsonProcessingException {
        log.info("Received order created succeeded event with payload: {}", event.getPayload());

        orderEventHandler.handleOrderCreatedSucceededEvent(event);
    }
}
