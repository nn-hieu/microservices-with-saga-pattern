package com.hieunn.orderservice.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hieunn.orderservice.entities.SagaEvent;

public interface PaymentEventHandler {
    void handleTransactionCompletedEvent(SagaEvent event) throws JsonProcessingException;
}
