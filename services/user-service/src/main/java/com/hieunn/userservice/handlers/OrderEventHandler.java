package com.hieunn.userservice.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hieunn.userservice.entities.SagaEvent;

public interface OrderEventHandler {
    void handleOrderCreatedSucceededEvent(SagaEvent event) throws JsonProcessingException;
}
