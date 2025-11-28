package com.hieunn.commonlib.repositories;

import com.hieunn.commonlib.entities.ProcessedSagaEventBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ProcessedSagaEventBaseRepository<T extends ProcessedSagaEventBase> extends JpaRepository<T, Integer> {
    boolean existsBySagaIdAndEventName(String sagaId, String eventName);
}