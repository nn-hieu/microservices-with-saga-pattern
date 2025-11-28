package com.hieunn.paymentservice.publishers;

import com.hieunn.commonlib.publishers.AbstractSagaEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SagaSagaEventPublisher extends AbstractSagaEventPublisher {
    public SagaSagaEventPublisher(ApplicationEventPublisher publisher) {
        super(publisher);
    }
}
