package com.hieunn.userservice.services;

import com.hieunn.commonlib.dtos.auths.LoginDto;

public interface AuthService {
    LoginDto.Response login(LoginDto.Request request);
}
