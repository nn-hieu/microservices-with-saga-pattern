package com.hieunn.deadletterservice.services;

import com.hieunn.commonlib.entities.SagaEventBase;
import com.hieunn.deadletterservice.entities.SagaEvent;

public interface SagaEventService {
    SagaEvent save(SagaEventBase event, String originalQueue);
}
