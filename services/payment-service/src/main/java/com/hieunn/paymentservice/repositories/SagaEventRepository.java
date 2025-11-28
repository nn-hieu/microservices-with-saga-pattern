package com.hieunn.paymentservice.repositories;

import com.hieunn.commonlib.repositories.SagaEventBaseRepository;
import com.hieunn.paymentservice.entities.SagaEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SagaEventRepository extends SagaEventBaseRepository<SagaEvent> {
}
