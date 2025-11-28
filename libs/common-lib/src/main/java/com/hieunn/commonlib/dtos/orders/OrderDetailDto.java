package com.hieunn.commonlib.dtos.orders;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class OrderDetailDto {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Integer id;
        private Integer productId;
        private Integer quantity;
        private Integer unitPrice;
        private Integer amount;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetailCreateRequest {
        private Integer productId;

        @Min(value = 1, message = "Quantity must be greater than 0")
        private Integer quantity;
    }
}
