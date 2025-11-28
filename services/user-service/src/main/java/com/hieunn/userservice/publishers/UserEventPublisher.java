package com.hieunn.userservice.publishers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hieunn.commonlib.dtos.events.SagaEventDto;

public interface UserEventPublisher {
    void publishUserDebitEvent(SagaEventDto orderCreatedSucceededSagaEvent, boolean isSuccessful) throws JsonProcessingException;
}
