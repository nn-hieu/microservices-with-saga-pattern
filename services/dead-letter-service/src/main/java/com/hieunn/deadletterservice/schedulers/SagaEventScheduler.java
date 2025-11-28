package com.hieunn.deadletterservice.schedulers;

import com.hieunn.commonlib.enums.events.OrderEvent;
import com.hieunn.commonlib.enums.events.PaymentEvent;
import com.hieunn.commonlib.enums.events.UserEvent;
import com.hieunn.deadletterservice.entities.SagaEvent;
import com.hieunn.deadletterservice.handlers.OrderEventHandler;
import com.hieunn.deadletterservice.handlers.PaymentEventHandler;
import com.hieunn.deadletterservice.handlers.UserEventHandler;
import com.hieunn.deadletterservice.repositories.SagaEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SagaEventScheduler {
    private final SagaEventRepository sagaEventRepository;
    private final OrderEventHandler orderEventHandler;
    private final UserEventHandler userEventHandler;
    private final PaymentEventHandler paymentEventHandler;

    @Scheduled(fixedDelayString = "${saga-event.polling-interval}")
    public void handleSagaEvent() {
        if (sagaEventRepository.count() == 0) {
            return;
        }

        List<SagaEvent> sagaEvents = sagaEventRepository.findAll();

        for (SagaEvent sagaEvent : sagaEvents) {
            if (!shouldRetryNow(sagaEvent)) continue;

            String eventName = sagaEvent.getEventName();

            if (OrderEvent.isOrderEvent(eventName)) {
                orderEventHandler.handleOrderEvent(sagaEvent);
            } else if (UserEvent.isUserEvent(eventName)) {
                userEventHandler.handleUserEvent(sagaEvent);
            } else if (PaymentEvent.isPaymentEvent(eventName)) {
                paymentEventHandler.handlePaymentEvent(sagaEvent);
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