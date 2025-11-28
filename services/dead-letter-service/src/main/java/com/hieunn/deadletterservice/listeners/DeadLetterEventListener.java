package com.hieunn.deadletterservice.listeners;

import com.hieunn.commonlib.entities.SagaEventBase;
import com.hieunn.deadletterservice.entities.SagaEvent;
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
    public void listenDeadLetterEvent(SagaEvent event, Message message) {
        log.info("Received event with name: {}, payload: {}", event.getEventName(), event.getPayload());

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> xDeathHeader =
                (List<Map<String, Object>>) message.getMessageProperties().getHeaders().get("x-death");

        String originalQueue = null;

        if (xDeathHeader != null && !xDeathHeader.isEmpty()) {
            originalQueue = (String) xDeathHeader.getFirst().get("queue");
        }

        sagaEventService.save(event, originalQueue);
    }
}