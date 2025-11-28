package com.hieunn.deadletterservice.handlers;

import com.hieunn.deadletterservice.entities.SagaEvent;

@FunctionalInterface
public interface OrderEventHandler {
    void handleOrderEvent(SagaEvent sagaEvent);
}