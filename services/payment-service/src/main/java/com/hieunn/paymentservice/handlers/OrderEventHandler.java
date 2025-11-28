package com.hieunn.paymentservice.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hieunn.paymentservice.entities.SagaEvent;

public interface OrderEventHandler {
    void handleOrderCreatedSucceededEvent(SagaEvent event) throws JsonProcessingException;
}
