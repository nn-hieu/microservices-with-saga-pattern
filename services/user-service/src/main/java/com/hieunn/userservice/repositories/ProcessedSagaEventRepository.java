package com.hieunn.userservice.repositories;

import com.hieunn.commonlib.repositories.ProcessedSagaEventBaseRepository;
import com.hieunn.userservice.entities.ProcessedSagaEvent;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessedSagaEventRepository extends ProcessedSagaEventBaseRepository<ProcessedSagaEvent> {
}