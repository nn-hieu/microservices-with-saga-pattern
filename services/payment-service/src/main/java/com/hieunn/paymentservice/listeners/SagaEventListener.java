package com.hieunn.paymentservice.listeners;

import com.hieunn.commonlib.enums.rabbitmqs.Exchange;
import com.hieunn.commonlib.listeners.AbstractSagaEventListener;
import com.hieunn.commonlib.repositories.SagaEventBaseRepository;
import com.hieunn.paymentservice.entities.SagaEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SagaEventListener extends AbstractSagaEventListener<SagaEvent> {
    public SagaEventListener(
            SagaEventBaseRepository<SagaEvent> sagaEventBaseRepository,
            RabbitTemplate rabbitTemplate
    ) {
        super(sagaEventBaseRepository, rabbitTemplate);
    }

    @Override
    protected String getExchange() {
        return Exchange.PAYMENT_EXCHANGE.getValue();
    }
}