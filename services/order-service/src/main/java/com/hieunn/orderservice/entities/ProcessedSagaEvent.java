package com.hieunn.orderservice.entities;

import com.hieunn.commonlib.entities.ProcessedSagaEventBase;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "processed_saga_events")
@Data
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
public class ProcessedSagaEvent extends ProcessedSagaEventBase {
}
