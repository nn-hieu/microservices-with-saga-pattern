package com.hieunn.userservice.controllers;

import com.hieunn.commonlib.dtos.ApiResponse;
import com.hieunn.commonlib.dtos.auths.LoginDTO;
import com.hieunn.userservice.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginDTO.Response>> login(@RequestBody LoginDTO.Request request) {
        LoginDTO.Response response = authService.login(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }
}
