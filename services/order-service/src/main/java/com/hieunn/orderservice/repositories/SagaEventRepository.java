package com.hieunn.orderservice.repositories;

import com.hieunn.commonlib.repositories.SagaEventBaseRepository;
import com.hieunn.orderservice.entities.SagaEvent;
import org.springframework.stereotype.Repository;

@Repository
public interface SagaEventRepository extends SagaEventBaseRepository<SagaEvent> {
}