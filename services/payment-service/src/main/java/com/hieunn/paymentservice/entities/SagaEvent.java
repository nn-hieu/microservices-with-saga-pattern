package com.hieunn.paymentservice.entities;

import com.hieunn.commonlib.entities.SagaEventBase;
import com.hieunn.commonlib.enums.constants.ServiceName;
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
    public SagaEvent() {
        this.setSourceService(ServiceName.PAYMENT_SERVICE.getValue());
    }
}