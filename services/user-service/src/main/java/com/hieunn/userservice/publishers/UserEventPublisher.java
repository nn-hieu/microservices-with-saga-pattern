package com.hieunn.userservice.publishers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hieunn.userservice.entities.SagaEvent;

public interface UserEventPublisher {
    void publishUserDebitEvent(SagaEvent orderCreatedSucceededSagaEvent, boolean isSuccessful) throws JsonProcessingException;
}
