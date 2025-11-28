package com.hieunn.commonlib.dtos.orders;

import com.hieunn.commonlib.enums.status.OrderStatus;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDto {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Integer id;
        private Integer userId;
        private Integer totalAmount;
        private OrderStatus status;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private List<OrderDetailDto.Response> orderDetails;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateRequest {
        private Integer userId;

        @Valid
        private List<OrderDetailDto.DetailCreateRequest> orderDetails;
    }
}
