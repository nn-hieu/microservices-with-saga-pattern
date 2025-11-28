package com.hieunn.paymentservice.handlers;

import com.hieunn.commonlib.handlers.AbstractProcessedSagaEventHandler;
import com.hieunn.commonlib.repositories.ProcessedSagaEventBaseRepository;
import com.hieunn.paymentservice.entities.ProcessedSagaEvent;

public abstract class AbstractOrderEventHandler extends AbstractProcessedSagaEventHandler<ProcessedSagaEvent> implements OrderEventHandler {
    public AbstractOrderEventHandler(
            Class<ProcessedSagaEvent> _T,
            ProcessedSagaEventBaseRepository<ProcessedSagaEvent> processedSagaEventBaseRepository
    ) {
        super(_T, processedSagaEventBaseRepository);
    }
}
