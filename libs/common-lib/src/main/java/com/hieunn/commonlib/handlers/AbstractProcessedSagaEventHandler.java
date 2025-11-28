package com.hieunn.commonlib.handlers;

import com.hieunn.commonlib.entities.ProcessedSagaEventBase;
import com.hieunn.commonlib.repositories.ProcessedSagaEventBaseRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractProcessedSagaEventHandler<T extends ProcessedSagaEventBase> {
    private final Class<T> _T;
    private final ProcessedSagaEventBaseRepository<T> processedSagaEventBaseRepository;

    protected void saveIdempotencyLog(String sagaId, String eventName) {
        try {
            T processedEvent = _T.getDeclaredConstructor().newInstance();
            processedEvent.setSagaId(sagaId);
            processedEvent.setEventName(eventName);
            processedSagaEventBaseRepository.save(processedEvent);
        } catch (Exception e) {
            throw new RuntimeException("Cannot instantiate class: " + _T.getName(), e);
        }
    }
}
