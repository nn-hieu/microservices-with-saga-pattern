package com.hieunn.paymentservice.handlers.impls;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hieunn.commonlib.dtos.events.UserDebitEvent;
import com.hieunn.commonlib.dtos.payments.TransactionDTO;
import com.hieunn.commonlib.enums.status.TransactionStatus;
import com.hieunn.commonlib.exceptions.NotFoundException;
import com.hieunn.paymentservice.entities.ProcessedSagaEvent;
import com.hieunn.paymentservice.entities.SagaEvent;
import com.hieunn.paymentservice.handlers.AbstractUserEventHandler;
import com.hieunn.paymentservice.publishers.PaymentEventPublisher;
import com.hieunn.paymentservice.repositories.ProcessedSagaEventRepository;
import com.hieunn.paymentservice.services.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class UserEventHandlerImpl extends AbstractUserEventHandler {
    private final TransactionService transactionService;
    private final ObjectMapper objectMapper;
    private final PaymentEventPublisher paymentEventPublisher;

    public UserEventHandlerImpl(
            ObjectMapper objectMapper,
            TransactionService transactionService,
            ProcessedSagaEventRepository processedSagaEventRepository,
            PaymentEventPublisher paymentEventPublisher
    ) {
        super(ProcessedSagaEvent.class, processedSagaEventRepository);
        this.objectMapper = objectMapper;
        this.transactionService = transactionService;
        this.paymentEventPublisher = paymentEventPublisher;
    }

    @Override
    @Retryable(
            retryFor = {NotFoundException.class},
            maxAttempts = 4,
            backoff = @Backoff(delay = 300)
    ) // Handle the race condition where the transaction is created but has not yet been committed to the database when the orderCreatedSucceeded event is processed
    @Transactional // Combine the transactions of the updateStatusByOrderId method and the saveIdempotencyLog into a single transaction
    public void handleUserDebitSucceededEvent(SagaEvent event) throws JsonProcessingException {
        try {
            UserDebitEvent userDebitEvent = objectMapper.readValue(event.getPayload(), UserDebitEvent.class);

            TransactionDTO.Response transaction = transactionService.updateStatusByOrderId(
                    userDebitEvent.getOrderId(),
                    TransactionStatus.COMPLETED
            );

            this.saveIdempotencyLog(event.getSagaId(), event.getEventName());

            paymentEventPublisher.publishTransactionCompletedEvent(event.getSagaId(), transaction);
        } catch (JsonProcessingException e) {
            log.error("Json exception: {}", e.getMessage());

            throw e;
        }
    }

    @Override
    @Retryable(
            retryFor = {NotFoundException.class},
            maxAttempts = 4,
            backoff = @Backoff(delay = 300)
    ) // Handle the race condition where the transaction is created but has not yet been committed to the database when the orderCreatedSucceeded event is processed
    @Transactional // Combine the transactions of the updateStatusByOrderId method and the saveIdempotencyLog into a single transaction
    public void handleUserDebitFailedEvent(SagaEvent event) throws JsonProcessingException {
        try {
            UserDebitEvent userDebitEvent = objectMapper.readValue(event.getPayload(), UserDebitEvent.class);

            transactionService.updateStatusByOrderId(
                    userDebitEvent.getOrderId(),
                    TransactionStatus.FAILED
            );

            this.saveIdempotencyLog(event.getSagaId(), event.getEventName());
        } catch (JsonProcessingException e) {
            log.error("Json exception: {}", e.getMessage());

            throw e;
        }
    }
}