package com.hieunn.orderservice.schedulers;

import com.hieunn.commonlib.publishers.AbstractSagaEventPublisher;
import com.hieunn.orderservice.entities.SagaEvent;
import com.hieunn.orderservice.repositories.SagaEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SagaEventScheduler {
    private final SagaEventRepository sagaEventRepository;
    private final AbstractSagaEventPublisher sagaEventPublisher;

    @Scheduled(fixedDelayString = "${saga-event.polling-interval}")
    @Transactional
    public void handleSagaEvent() {
        if (sagaEventRepository.count() == 0) {
            return;
        }

        List<SagaEvent> sagaEvents = sagaEventRepository.findAll();

        for (SagaEvent sagaEvent : sagaEvents) {
            if (shouldRetryNow(sagaEvent)) {
                sagaEventPublisher.publishEvent(sagaEvent);
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
