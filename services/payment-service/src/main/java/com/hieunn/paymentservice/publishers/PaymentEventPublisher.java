package com.hieunn.paymentservice.publishers;

import com.hieunn.commonlib.dtos.payments.TransactionDto;

public interface PaymentEventPublisher {
    void publishTransactionCompletedEvent(String sagaId, TransactionDto.Response transaction);
}
