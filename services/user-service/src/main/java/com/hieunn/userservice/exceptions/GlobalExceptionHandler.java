package com.hieunn.userservice.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hieunn.commonlib.dtos.ApiResponse;
import com.hieunn.commonlib.exceptions.AbstractGlobalExceptionHandler;
import com.hieunn.commonlib.exceptions.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler extends AbstractGlobalExceptionHandler {
    public GlobalExceptionHandler(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiResponse<Void>> handleUnauthorizedException(UnauthorizedException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error(
                                HttpStatus.UNAUTHORIZED,
                                ex.getMessage()
                        )
                );
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ApiResponse<Void>> handleInsufficientFundsException(InsufficientFundsException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ApiResponse.error(
                                HttpStatus.CONFLICT,
                                ex.getMessage()
                        )
                );
    }
}
