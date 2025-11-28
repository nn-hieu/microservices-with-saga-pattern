package com.hieunn.userservice.services;

import com.hieunn.commonlib.dtos.users.UserDTO;

public interface UserService {
    UserDTO.Response register(UserDTO.CreateRequest request);

    UserDTO.Response deductBalance(Integer userId, Integer amount);
}
