package com.hieunn.orderservice.listeners;

import com.hieunn.commonlib.enums.rabbitmqs.Exchange;
import com.hieunn.commonlib.listeners.AbstractSagaEventListener;
import com.hieunn.commonlib.repositories.SagaEventBaseRepository;
import com.hieunn.orderservice.entities.SagaEvent;
import com.hieunn.orderservice.mappers.SagaEventMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class SagaEventListener extends AbstractSagaEventListener<SagaEvent> {
    public SagaEventListener(
            SagaEventBaseRepository<SagaEvent> sagaEventBaseRepository,
            RabbitTemplate rabbitTemplate,
            SagaEventMapper sagaEventMapper
    ) {
        super(sagaEventBaseRepository, rabbitTemplate, sagaEventMapper);
    }

    @Override
    protected String getExchange() {
        return Exchange.ORDER_EXCHANGE.getValue();
    }
}