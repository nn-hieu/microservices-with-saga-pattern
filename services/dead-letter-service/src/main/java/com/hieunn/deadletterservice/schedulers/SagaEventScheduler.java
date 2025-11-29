package com.hieunn.deadletterservice.schedulers;

import com.hieunn.commonlib.enums.constants.ServiceName;
import com.hieunn.deadletterservice.entities.SagaEvent;
import com.hieunn.deadletterservice.mappers.SagaEventMapper;
import com.hieunn.deadletterservice.repositories.SagaEventRepository;
import com.hieunn.deadletterservice.utils.EurekaUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SagaEventScheduler {
    private final SagaEventRepository sagaEventRepository;
    private final EurekaUtil eurekaUtil;
    private final RabbitTemplate rabbitTemplate;
    private final SagaEventMapper sagaEventMapper;

    @Scheduled(fixedDelayString = "${saga-event.polling-interval}")
    @Transactional
    public void handleSagaEvent() {
        if (sagaEventRepository.count() == 0) {
            return;
        }

        List<SagaEvent> events = sagaEventRepository.findAll();

        for (SagaEvent event : events) {
            if (!shouldRetryNow(event)) continue;

            String[] queueParts = event.getQueue().split("\\.");
            ServiceName serviceName = ServiceName.fromValue(queueParts[queueParts.length - 1]);

            if (eurekaUtil.areServicesUp(serviceName)) {
                try {
                    rabbitTemplate.convertAndSend(
                            event.getExchange(),
                            event.getEventName(),
                            sagaEventMapper.toDto(event)
                    );

                    sagaEventRepository.delete(event);
                } catch (AmqpException e) {
                    event.setRetryCount(event.getRetryCount() + 1);
                    event.setLastAttemptAt(LocalDateTime.now());

                    sagaEventRepository.save(event);
                }
            }
        }
    }

    private boolean shouldRetryNow(SagaEvent sagaEvent) {
        if (sagaEvent.getLastAttemptAt() == null) return true;

        long delayMinutes = getBackoffDelayMinutes(sagaEvent.getRetryCount());
        LocalDateTime nextAttemptTime = sagaEvent.getLastAttemptAt().plusMinutes(delayMinutes);
        return LocalDateTime.now().isAfter(nextAttemptTime);
    }

    private long getBackoffDelayMinutes(int retryCount) {
        if (retryCount < 5) return 1;
        if (retryCount < 10) return 5;
        if (retryCount < 15) return 10;
        if (retryCount < 20) return 30;
        return 60;
    }
}