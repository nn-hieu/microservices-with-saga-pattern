package com.hieunn.deadletterservice.services;

import com.hieunn.commonlib.dtos.events.SagaEventDto;
import com.hieunn.deadletterservice.entities.SagaEvent;

public interface SagaEventService {
    SagaEvent save(SagaEventDto event, String queue, String exchange);
}
