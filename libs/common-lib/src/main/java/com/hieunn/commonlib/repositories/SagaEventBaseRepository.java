package com.hieunn.commonlib.repositories;

import com.hieunn.commonlib.entities.SagaEventBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface SagaEventBaseRepository<T extends SagaEventBase> extends JpaRepository<T, Integer> {
}
