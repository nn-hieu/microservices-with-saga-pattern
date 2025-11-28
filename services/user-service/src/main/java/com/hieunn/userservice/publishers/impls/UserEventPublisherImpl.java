package com.hieunn.userservice.publishers.impls;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hieunn.commonlib.dtos.events.OrderCreatedEvent;
import com.hieunn.commonlib.dtos.events.UserDebitEvent;
import com.hieunn.commonlib.enums.events.UserEvent;
import com.hieunn.commonlib.publishers.AbstractSagaEventPublisher;
import com.hieunn.userservice.entities.SagaEvent;
import com.hieunn.userservice.publishers.UserEventPublisher;
import com.hieunn.userservice.repositories.SagaEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserEventPublisherImpl implements UserEventPublisher {
    private final SagaEventRepository sagaEventRepository;
    private final AbstractSagaEventPublisher sagaEventPublisher;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    @Async
    public void publishUserDebitEvent(SagaEvent orderCreatedSucceededSagaEvent, boolean isSuccessful)
            throws JsonProcessingException
    {
        try {
            OrderCreatedEvent orderCreatedEvent = objectMapper.readValue(orderCreatedSucceededSagaEvent.getPayload(), OrderCreatedEvent.class);

            UserDebitEvent userDebitEvent = new UserDebitEvent();
            userDebitEvent.setOrderId(orderCreatedEvent.getOrderId());
            userDebitEvent.setUserId(orderCreatedEvent.getUserId());
            userDebitEvent.setTotalAmount(orderCreatedEvent.getTotalAmount());
            userDebitEvent.setIsSuccess(isSuccessful);

            String eventName = isSuccessful ? UserEvent.USER_DEBIT_SUCCEEDED.getValue() : UserEvent.USER_DEBIT_FAILED.getValue();

            SagaEvent sagaEvent = new SagaEvent();
            sagaEvent.setEventName(eventName);
            sagaEvent.setSagaId(orderCreatedSucceededSagaEvent.getSagaId());
            sagaEvent.setPayload(objectMapper.writeValueAsString(userDebitEvent));

            sagaEventRepository.save(sagaEvent);

            log.info(
                    "Publish user debit {} event with payload: {}",
                    isSuccessful ? "succeeded" : "failed",
                    sagaEvent.getPayload()
            );

            sagaEventPublisher.publishEvent(sagaEvent);
        } catch (JsonProcessingException e) {
            log.error("Json exception: {}", e.getMessage());

            throw e;
        }
    }
}
