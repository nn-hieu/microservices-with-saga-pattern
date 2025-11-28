package com.hieunn.commonlib.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hieunn.commonlib.dtos.ApiResponse;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public abstract class AbstractGlobalExceptionHandler {
    private final ObjectMapper objectMapper;

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(
                                HttpStatus.NOT_FOUND,
                                ex.getMessage()
                        )
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(
                                HttpStatus.INTERNAL_SERVER_ERROR,
                                ex.getMessage()
                        )
                );
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiResponse<Void>> handleResponseStatusException(ResponseStatusException ex) {
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(ApiResponse.error(
                                (HttpStatus) ex.getStatusCode(),
                                ex.getReason()
                        )
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return ResponseEntity
                .status(ex.getStatusCode())
                .body(ApiResponse.error(
                                (HttpStatus) ex.getStatusCode(),
                                "Validation Failed",
                                errors
                        )
                );
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ApiResponse<?>> handleFeignException(FeignException ex) {
        try {
            ApiResponse<?> response = objectMapper.readValue(ex.contentUTF8(), ApiResponse.class);
            response.setStatus(500);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        } catch (JsonProcessingException e) {
            ApiResponse<Void> error = ApiResponse.error(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Internal Server Error"
            );
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(error);
        }
    }
}
