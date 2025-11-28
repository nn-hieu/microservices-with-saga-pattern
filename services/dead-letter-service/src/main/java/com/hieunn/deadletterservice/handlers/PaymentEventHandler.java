package com.hieunn.deadletterservice.handlers;

import com.hieunn.deadletterservice.entities.SagaEvent;

@FunctionalInterface
public interface PaymentEventHandler {
    void handlePaymentEvent(SagaEvent sagaEvent);
}
