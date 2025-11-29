package com.hieunn.deadletterservice.listeners;

import com.hieunn.commonlib.dtos.events.SagaEventDto;
import com.hieunn.deadletterservice.enums.RabbitMQQueue;
import com.hieunn.deadletterservice.services.SagaEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeadLetterEventListener {
    private final SagaEventService sagaEventService;

    @RabbitListener(queues = RabbitMQQueue.DQL_NAME)
    public void listenDeadLetterEvent(SagaEventDto event, Message message) {
        log.info("Received event with name: {}, payload: {}", event.getEventName(), event.getPayload());

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> xDeathHeader =
                (List<Map<String, Object>>) message.getMessageProperties().getHeaders().get("x-death");

        String queue = "";
        String exchange = "";

        if (xDeathHeader != null && !xDeathHeader.isEmpty()) {
            Map<String, Object> deathInfo = xDeathHeader.getFirst();
            queue = (String) deathInfo.get("queue");
            exchange = (String) deathInfo.get("exchange");
        }

        sagaEventService.save(event, queue, exchange);
    }
}