package com.hieunn.orderservice.handlers.impls;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hieunn.commonlib.dtos.events.TransactionCompletedEvent;
import com.hieunn.orderservice.entities.ProcessedSagaEvent;
import com.hieunn.orderservice.entities.SagaEvent;
import com.hieunn.orderservice.handlers.AbstractPaymentEventHandler;
import com.hieunn.orderservice.repositories.ProcessedSagaEventRepository;
import com.hieunn.orderservice.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class PaymentEventHandlerImpl extends AbstractPaymentEventHandler {
    private final ObjectMapper objectMapper;
    private final OrderService orderService;

    public PaymentEventHandlerImpl(
            ObjectMapper objectMapper,
            OrderService orderService,
            ProcessedSagaEventRepository processedSagaEventRepository
    ) {
        super(ProcessedSagaEvent.class, processedSagaEventRepository);
        this.objectMapper = objectMapper;
        this.orderService = orderService;
    }

    @Override
    @Transactional //Combine the transactions of the updateOrderCompleted method and the saveIdempotencyLog into a single transaction
    public void handleTransactionCompletedEvent(SagaEvent event) throws JsonProcessingException {
        try {
            TransactionCompletedEvent transactionCompletedEvent = objectMapper.readValue(event.getPayload(), TransactionCompletedEvent.class);

            orderService.updateOrderCompleted(transactionCompletedEvent.getOrderId());

            this.saveIdempotencyLog(event.getSagaId(), event.getEventName());
        } catch (JsonProcessingException e) {
            log.error("Json exception: {}", e.getMessage());

            throw e;
        }
    }
}
