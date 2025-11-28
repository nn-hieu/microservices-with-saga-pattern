package com.hieunn.paymentservice.publishers.impls;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hieunn.commonlib.dtos.payments.TransactionDTO;
import com.hieunn.commonlib.enums.events.PaymentEvent;
import com.hieunn.commonlib.publishers.AbstractSagaEventPublisher;
import com.hieunn.paymentservice.entities.SagaEvent;
import com.hieunn.commonlib.dtos.events.TransactionCompletedEvent;
import com.hieunn.paymentservice.publishers.PaymentEventPublisher;
import com.hieunn.paymentservice.repositories.SagaEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentEventPublisherImpl implements PaymentEventPublisher {
    private final SagaEventRepository sagaEventRepository;
    private final AbstractSagaEventPublisher sagaEventPublisher;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    @Async
    public void publishTransactionCompletedEvent(String sagaId, TransactionDTO.Response transaction) {
        try {
            TransactionCompletedEvent transactionCompletedEvent = new TransactionCompletedEvent();
            transactionCompletedEvent.setTransactionId(transaction.getId());
            transactionCompletedEvent.setWalletId(transaction.getWalletId());
            transactionCompletedEvent.setOrderId(transaction.getOrderId());
            transactionCompletedEvent.setAmount(transaction.getAmount());
            transactionCompletedEvent.setStatus(transaction.getStatus());

            SagaEvent sagaEvent = new SagaEvent();
            sagaEvent.setEventName(PaymentEvent.TRANSACTION_COMPLETED.getValue());
            sagaEvent.setSagaId(sagaId);
            sagaEvent.setPayload(objectMapper.writeValueAsString(transactionCompletedEvent));

            sagaEventRepository.save(sagaEvent);

            log.info("Publish transaction completed event with payload: {}", sagaEvent.getPayload());

            sagaEventPublisher.publishEvent(sagaEvent);
        } catch (JsonProcessingException e) {
            log.error("Json exception: {}", e.getMessage());
        }
    }
}
