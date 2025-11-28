package com.hieunn.userservice.mappers;

import com.hieunn.commonlib.dtos.users.WalletDTO;
import com.hieunn.userservice.entities.Wallet;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WalletMapper {
    WalletDTO.Response toResponse(Wallet wallet);
}
