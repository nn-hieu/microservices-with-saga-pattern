package com.hieunn.userservice.listeners;

import com.hieunn.commonlib.enums.rabbitmqs.Exchange;
import com.hieunn.commonlib.listeners.AbstractSagaEventListener;
import com.hieunn.commonlib.repositories.SagaEventBaseRepository;
import com.hieunn.userservice.entities.SagaEvent;
import com.hieunn.userservice.mappers.SagaEventMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
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
        return Exchange.USER_EXCHANGE.getValue();
    }
}
