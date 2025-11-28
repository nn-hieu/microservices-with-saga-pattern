package com.hieunn.paymentservice.publishers;

import com.hieunn.commonlib.dtos.payments.TransactionDTO;

public interface PaymentEventPublisher {
    void publishTransactionCompletedEvent(String sagaId, TransactionDTO.Response transaction);
}
