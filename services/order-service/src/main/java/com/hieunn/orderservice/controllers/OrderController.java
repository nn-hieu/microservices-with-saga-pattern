package com.hieunn.orderservice.controllers;

import com.hieunn.commonlib.dtos.ApiResponse;
import com.hieunn.commonlib.dtos.orders.OrderDto;
import com.hieunn.orderservice.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse<OrderDto.Response>> create(@RequestBody @Valid OrderDto.CreateRequest request) {
        OrderDto.Response response = orderService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

    @PostMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> updateOrderFailed(@PathVariable Integer id) {
        orderService.updateOrderFailed(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success("Success"));
    }
}
