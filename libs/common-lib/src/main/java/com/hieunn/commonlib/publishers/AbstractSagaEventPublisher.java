package com.hieunn.commonlib.publishers;

import com.hieunn.commonlib.entities.SagaEventBase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

@RequiredArgsConstructor
public abstract class AbstractSagaEventPublisher {
    private final ApplicationEventPublisher publisher;

    public void publishEvent(SagaEventBase sagaEventBase) {
        publisher.publishEvent(sagaEventBase);
    }
}