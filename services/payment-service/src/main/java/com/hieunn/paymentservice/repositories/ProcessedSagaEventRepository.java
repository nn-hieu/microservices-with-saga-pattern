package com.hieunn.paymentservice.repositories;

import com.hieunn.commonlib.repositories.ProcessedSagaEventBaseRepository;
import com.hieunn.paymentservice.entities.ProcessedSagaEvent;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessedSagaEventRepository extends ProcessedSagaEventBaseRepository<ProcessedSagaEvent> {
}