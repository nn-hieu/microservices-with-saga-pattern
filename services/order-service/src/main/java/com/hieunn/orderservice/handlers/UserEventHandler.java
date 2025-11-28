package com.hieunn.orderservice.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hieunn.commonlib.dtos.events.SagaEventDto;

public interface UserEventHandler {
    void handleUserDebitFailedEvent(SagaEventDto event) throws JsonProcessingException;
}