package com.hieunn.paymentservice.services;

import com.hieunn.commonlib.dtos.payments.TransactionDTO;
import com.hieunn.commonlib.enums.status.TransactionStatus;
import com.hieunn.paymentservice.entities.Transaction;

public interface TransactionService {
    TransactionDTO.Response create(TransactionDTO.CreateRequest request);

    TransactionDTO.Response updateStatus(Transaction transaction, TransactionStatus status);

    TransactionDTO.Response updateStatusByOrderId(Integer orderId, TransactionStatus status);
}