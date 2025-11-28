package com.hieunn.paymentservice.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hieunn.paymentservice.entities.SagaEvent;

public interface UserEventHandler {
    void handleUserDebitSucceededEvent(SagaEvent event) throws JsonProcessingException;

    void handleUserDebitFailedEvent(SagaEvent event) throws JsonProcessingException;
}
