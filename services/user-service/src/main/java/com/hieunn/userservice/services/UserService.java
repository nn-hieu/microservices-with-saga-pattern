package com.hieunn.userservice.services;

import com.hieunn.commonlib.dtos.users.UserDto;

public interface UserService {
    UserDto.Response register(UserDto.CreateRequest request);

    UserDto.Response deductBalance(Integer userId, Integer amount);
}
