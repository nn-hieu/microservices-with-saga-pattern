package com.hieunn.orderservice.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hieunn.commonlib.exceptions.AbstractGlobalExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler extends AbstractGlobalExceptionHandler {
    public GlobalExceptionHandler(ObjectMapper objectMapper) {
        super(objectMapper);
    }
}
