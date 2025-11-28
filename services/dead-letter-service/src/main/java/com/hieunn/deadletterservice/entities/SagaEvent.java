package com.hieunn.deadletterservice.entities;

import com.hieunn.commonlib.entities.SagaEventBase;
import com.hieunn.commonlib.enums.constants.ServiceName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "saga_events")
@Data
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
public class SagaEvent extends SagaEventBase {
//    @Column(nullable = false, length = 50)
//    private String originalQueue;
}
