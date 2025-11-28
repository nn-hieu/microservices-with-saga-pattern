package com.hieunn.paymentservice.controllers;

import com.hieunn.commonlib.dtos.ApiResponse;
import com.hieunn.commonlib.dtos.payments.TransactionDTO;
import com.hieunn.paymentservice.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<ApiResponse<TransactionDTO.Response>> create(@RequestBody TransactionDTO.CreateRequest request) {
        TransactionDTO.Response response = transactionService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }
}