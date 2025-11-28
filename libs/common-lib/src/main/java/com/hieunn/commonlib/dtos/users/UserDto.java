package com.hieunn.commonlib.dtos.users;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class UserDto {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Integer id;
        private String username;
        private WalletDto.Response wallet;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateRequest {
        @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
        private String username;
        @Size(min = 3, max = 50, message = "Password must be between 3 and 50 characters")
        private String password;
    }
}
