package com.hieunn.userservice.services;

import com.hieunn.commonlib.dtos.auths.LoginDTO;

public interface AuthService {
    LoginDTO.Response login(LoginDTO.Request request);
}
