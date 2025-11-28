package com.hieunn.paymentservice.services.impls;

import com.hieunn.commonlib.dtos.payments.TransactionDTO;
import com.hieunn.commonlib.enums.status.TransactionStatus;
import com.hieunn.commonlib.exceptions.NotFoundException;
import com.hieunn.paymentservice.entities.Transaction;
import com.hieunn.paymentservice.mappers.TransactionMapper;
import com.hieunn.paymentservice.repositories.TransactionRepository;
import com.hieunn.paymentservice.services.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    @Override
    @Transactional
    public TransactionDTO.Response create(TransactionDTO.CreateRequest request) {
        Transaction transaction = new Transaction();
        transaction.setWalletId(request.getWalletId());
        transaction.setOrderId(request.getOrderId());
        transaction.setAmount(request.getAmount());

        transactionRepository.save(transaction);

        log.info("Create new transaction with user id: {} and amount: {}", transaction.getWalletId(), transaction.getAmount());

        return transactionMapper.toResponse(transaction);
    }

    @Override
    @Transactional
    public TransactionDTO.Response updateStatus(Transaction transaction, TransactionStatus status) {
        transaction.setStatus(status);

        transactionRepository.save(transaction);

        log.info("Update transaction status with user id: {} and status: {}", transaction.getWalletId(), transaction.getStatus());

        return transactionMapper.toResponse(transaction);
    }

    @Override
    @Transactional
    public TransactionDTO.Response updateStatusByOrderId(Integer orderId, TransactionStatus status) {
        Transaction transaction = transactionRepository.findById(orderId)
                .orElseThrow(() -> {
                    log.error("Transaction not found with order id: {}", orderId);

                    return new NotFoundException("Transaction not found with order id: " + orderId);
                });

        return this.updateStatus(transaction, status);
    }
}
