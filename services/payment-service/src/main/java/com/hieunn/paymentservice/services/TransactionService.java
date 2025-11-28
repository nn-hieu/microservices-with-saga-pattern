package com.hieunn.paymentservice.services;

import com.hieunn.commonlib.dtos.payments.TransactionDto;
import com.hieunn.commonlib.enums.status.TransactionStatus;
import com.hieunn.paymentservice.entities.Transaction;

public interface TransactionService {
    TransactionDto.Response create(TransactionDto.CreateRequest request);

    TransactionDto.Response updateStatus(Transaction transaction, TransactionStatus status);

    TransactionDto.Response updateStatusByOrderId(Integer orderId, TransactionStatus status);
}