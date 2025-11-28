package com.hieunn.orderservice.repositories;

import com.hieunn.commonlib.repositories.ProcessedSagaEventBaseRepository;
import com.hieunn.orderservice.entities.ProcessedSagaEvent;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessedSagaEventRepository extends ProcessedSagaEventBaseRepository<ProcessedSagaEvent> {
}