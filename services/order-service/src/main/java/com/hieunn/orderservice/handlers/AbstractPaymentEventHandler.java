package com.hieunn.orderservice.handlers;

import com.hieunn.commonlib.handlers.AbstractProcessedSagaEventHandler;
import com.hieunn.commonlib.repositories.ProcessedSagaEventBaseRepository;
import com.hieunn.orderservice.entities.ProcessedSagaEvent;

public abstract class AbstractPaymentEventHandler extends AbstractProcessedSagaEventHandler<ProcessedSagaEvent> implements PaymentEventHandler {
    public AbstractPaymentEventHandler(
            Class<ProcessedSagaEvent> _T,
            ProcessedSagaEventBaseRepository<ProcessedSagaEvent> processedSagaEventBaseRepository
    ) {
        super(_T, processedSagaEventBaseRepository);
    }
}