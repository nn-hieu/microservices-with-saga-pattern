package com.hieunn.orderservice.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hieunn.orderservice.entities.SagaEvent;
import com.hieunn.orderservice.enums.RabbitMQQueue;
import com.hieunn.orderservice.handlers.PaymentEventHandler;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentEventListener {
    private final PaymentEventHandler paymentEventHandler;

    @RabbitListener(queues = RabbitMQQueue.TRANSACTION_COMPLETED_QUEUE_NAME)
    public void listenTransactionCompletedEvent(
            SagaEvent event,
            @SuppressWarnings("unused") Message message,
            @SuppressWarnings("unused") Channel channel
    ) throws JsonProcessingException {
        log.info("Received transaction completed event with payload: {}", event.getPayload());

        paymentEventHandler.handleTransactionCompletedEvent(event);
    }
}
