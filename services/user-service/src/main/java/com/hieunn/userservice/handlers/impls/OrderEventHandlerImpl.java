package com.hieunn.userservice.handlers.impls;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hieunn.commonlib.dtos.events.OrderCreatedEvent;
import com.hieunn.commonlib.dtos.events.SagaEventDto;
import com.hieunn.userservice.entities.ProcessedSagaEvent;
import com.hieunn.userservice.exceptions.InsufficientFundsException;
import com.hieunn.userservice.handlers.AbstractOrderEventHandler;
import com.hieunn.userservice.publishers.UserEventPublisher;
import com.hieunn.userservice.repositories.ProcessedSagaEventRepository;
import com.hieunn.userservice.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class OrderEventHandlerImpl extends AbstractOrderEventHandler {
    private final UserService userService;
    private final UserEventPublisher userEventPublisher;
    private final ObjectMapper objectMapper;

    public OrderEventHandlerImpl(
            UserService userService,
            UserEventPublisher userEventPublisher,
            ObjectMapper objectMapper,
            ProcessedSagaEventRepository processedSagaEventRepository
    ) {
        super(ProcessedSagaEvent.class, processedSagaEventRepository);
        this.userService = userService;
        this.userEventPublisher = userEventPublisher;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional // Combine the transactions of the deductBalance method and the saveIdempotencyLog into a single transaction
    public void handleOrderCreatedSucceededEvent(SagaEventDto event) throws JsonProcessingException {
        try {
            OrderCreatedEvent orderCreatedEvent = objectMapper.readValue(event.getPayload(), OrderCreatedEvent.class);

            userService.deductBalance(orderCreatedEvent.getUserId(), orderCreatedEvent.getTotalAmount());

            this.saveIdempotencyLog(event.getSagaId(), event.getEventName());

            userEventPublisher.publishUserDebitEvent(event, true);
        } catch (InsufficientFundsException e) {
            this.saveIdempotencyLog(event.getSagaId(), event.getEventName());

            userEventPublisher.publishUserDebitEvent(event, false);
        } catch (JsonProcessingException e) {
            log.error("Json exception: {}", e.getMessage());

            throw e;
        }
    }
}