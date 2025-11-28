package com.hieunn.userservice.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hieunn.commonlib.dtos.events.SagaEventDto;

public interface OrderEventHandler {
    void handleOrderCreatedSucceededEvent(SagaEventDto event) throws JsonProcessingException;
}
