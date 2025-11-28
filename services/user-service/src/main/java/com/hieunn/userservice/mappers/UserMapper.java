package com.hieunn.userservice.mappers;

import com.hieunn.commonlib.dtos.users.UserDTO;
import com.hieunn.userservice.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {WalletMapper.class})
public interface UserMapper {
    UserDTO.Response toResponse(User user);
}
