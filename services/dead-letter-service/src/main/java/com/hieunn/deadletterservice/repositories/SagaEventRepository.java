package com.hieunn.deadletterservice.repositories;

import com.hieunn.deadletterservice.entities.SagaEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SagaEventRepository extends JpaRepository<SagaEvent, Integer> {
}
