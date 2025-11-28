package com.hieunn.paymentservice.mappers;

import com.hieunn.commonlib.dtos.payments.TransactionDTO;
import com.hieunn.paymentservice.entities.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionDTO.Response toResponse(Transaction transaction);
}
