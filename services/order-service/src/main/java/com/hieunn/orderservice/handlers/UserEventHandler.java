package com.hieunn.orderservice.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hieunn.orderservice.entities.SagaEvent;

public interface UserEventHandler {
    void handleUserDebitFailedEvent(SagaEvent event) throws JsonProcessingException;
}