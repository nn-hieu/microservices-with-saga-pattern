package com.hieunn.paymentservice.handlers.impls;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hieunn.commonlib.dtos.events.OrderCreatedEvent;
import com.hieunn.commonlib.dtos.events.SagaEventDto;
import com.hieunn.commonlib.dtos.payments.TransactionDto;
import com.hieunn.paymentservice.entities.ProcessedSagaEvent;
import com.hieunn.paymentservice.handlers.AbstractOrderEventHandler;
import com.hieunn.paymentservice.repositories.ProcessedSagaEventRepository;
import com.hieunn.paymentservice.services.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class OrderEventHandlerImpl extends AbstractOrderEventHandler {
    private final TransactionService transactionService;
    private final ObjectMapper objectMapper;

    public OrderEventHandlerImpl(
            TransactionService transactionService,
            ObjectMapper objectMapper,
            ProcessedSagaEventRepository processedSagaEventRepository
    ) {
        super(ProcessedSagaEvent.class, processedSagaEventRepository);
        this.transactionService = transactionService;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional // Combine the transactions of the create method and the saveIdempotencyLog into a single transaction
    public void handleOrderCreatedSucceededEvent(SagaEventDto event) throws JsonProcessingException {
        try {
            OrderCreatedEvent orderCreatedEvent = objectMapper.readValue(event.getPayload(), OrderCreatedEvent.class);

            TransactionDto.CreateRequest request = new TransactionDto.CreateRequest();
            request.setWalletId(orderCreatedEvent.getUserId());
            request.setOrderId(orderCreatedEvent.getOrderId());
            request.setAmount(orderCreatedEvent.getTotalAmount());

            transactionService.create(request);

            this.saveIdempotencyLog(event.getSagaId(), event.getEventName());
        } catch (JsonProcessingException e) {
            log.error("Json exception: {}", e.getMessage());

            throw e;
        }
    }
}
