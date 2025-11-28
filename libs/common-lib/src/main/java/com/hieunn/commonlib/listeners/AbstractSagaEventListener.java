package com.hieunn.commonlib.listeners;

import com.hieunn.commonlib.entities.SagaEventBase;
import com.hieunn.commonlib.mappers.SagaEventBaseMapper;
import com.hieunn.commonlib.repositories.SagaEventBaseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Slf4j
public abstract class AbstractSagaEventListener<T extends SagaEventBase> {
    private final SagaEventBaseRepository<T> sagaEventBaseRepository;
    private final RabbitTemplate rabbitTemplate;
    private final SagaEventBaseMapper<T> sagaEventBaseMapper;

    protected abstract String getExchange();

    @EventListener
    @Transactional
    public void listenSagaEvent(T sagaEvent) {
        try {
            rabbitTemplate.convertAndSend(
                    this.getExchange(),
                    sagaEvent.getEventName(),
                    sagaEventBaseMapper.toDto(sagaEvent)
            );

            sagaEventBaseRepository.delete(sagaEvent);

            log.info(
                    "Send saga event to RabbitMQ with name: {}, payload: {}, saga id: {}",
                    sagaEvent.getEventName(), sagaEvent.getPayload(), sagaEvent.getSagaId()
            );
        } catch (AmqpException e) {
            sagaEvent.setRetryCount(sagaEvent.getRetryCount() + 1);
            sagaEvent.setLastAttemptAt(LocalDateTime.now());

            sagaEventBaseRepository.save(sagaEvent);
        }
    }
}