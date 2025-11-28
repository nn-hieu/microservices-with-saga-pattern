package com.hieunn.orderservice.handlers.impls;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hieunn.commonlib.dtos.events.UserDebitEvent;
import com.hieunn.orderservice.entities.ProcessedSagaEvent;
import com.hieunn.orderservice.entities.SagaEvent;
import com.hieunn.orderservice.handlers.AbstractUserEventHandler;
import com.hieunn.orderservice.repositories.ProcessedSagaEventRepository;
import com.hieunn.orderservice.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class UserEventHandlerImpl extends AbstractUserEventHandler {
    private final ObjectMapper objectMapper;
    private final OrderService orderService;

    public UserEventHandlerImpl(
            ObjectMapper objectMapper,
            OrderService orderService,
            ProcessedSagaEventRepository processedSagaEventRepository
    ) {
        super(ProcessedSagaEvent.class, processedSagaEventRepository);
        this.objectMapper = objectMapper;
        this.orderService = orderService;
    }

    @Override
    @Transactional //Combine the transactions of the updateOrderFailed method and the saveIdempotencyLog into a single transaction
    public void handleUserDebitFailedEvent(SagaEvent event) throws JsonProcessingException {
        try {
            UserDebitEvent userDebitEvent = objectMapper.readValue(event.getPayload(), UserDebitEvent.class);

            orderService.updateOrderFailed(userDebitEvent.getOrderId());

            this.saveIdempotencyLog(event.getSagaId(), event.getEventName());
        } catch (JsonProcessingException e) {
            log.error("Json exception: {}", e.getMessage());

            throw e;
        }
    }
}
