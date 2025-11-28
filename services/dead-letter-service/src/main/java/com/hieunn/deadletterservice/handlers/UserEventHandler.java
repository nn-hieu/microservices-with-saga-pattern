package com.hieunn.deadletterservice.handlers;

import com.hieunn.deadletterservice.entities.SagaEvent;

@FunctionalInterface
public interface UserEventHandler {
    void handleUserEvent(SagaEvent sagaEvent);
}
