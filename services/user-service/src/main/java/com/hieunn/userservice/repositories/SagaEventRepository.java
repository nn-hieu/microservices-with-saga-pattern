package com.hieunn.userservice.repositories;

import com.hieunn.commonlib.repositories.SagaEventBaseRepository;
import com.hieunn.userservice.entities.SagaEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SagaEventRepository extends SagaEventBaseRepository<SagaEvent> {
}
