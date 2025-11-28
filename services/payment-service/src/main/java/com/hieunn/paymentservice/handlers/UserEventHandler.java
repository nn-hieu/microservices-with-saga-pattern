package com.hieunn.paymentservice.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hieunn.commonlib.dtos.events.SagaEventDto;

public interface UserEventHandler {
    void handleUserDebitSucceededEvent(SagaEventDto event) throws JsonProcessingException;

    void handleUserDebitFailedEvent(SagaEventDto event) throws JsonProcessingException;
}
