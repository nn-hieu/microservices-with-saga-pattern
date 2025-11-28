package com.hieunn.deadletterservice.services;

import com.hieunn.deadletterservice.entities.SagaEvent;

public interface SagaEventService {
    SagaEvent save(SagaEvent event, String originalQueue);
}
