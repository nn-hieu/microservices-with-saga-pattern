package com.hieunn.userservice.mappers;

import com.hieunn.commonlib.dtos.users.WalletDto;
import com.hieunn.userservice.entities.Wallet;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WalletMapper {
    WalletDto.Response toResponse(Wallet wallet);
}
