package com.hieunn.paymentservice.mappers;

import com.hieunn.commonlib.dtos.payments.TransactionDto;
import com.hieunn.paymentservice.entities.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionDto.Response toResponse(Transaction transaction);
}
