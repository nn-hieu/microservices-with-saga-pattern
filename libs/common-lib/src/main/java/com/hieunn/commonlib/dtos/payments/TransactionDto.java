package com.hieunn.commonlib.dtos.payments;

import com.hieunn.commonlib.enums.status.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class TransactionDto {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Integer id;
        private Integer walletId;
        private Integer orderId;
        private Integer amount;
        private TransactionStatus status;
        private LocalDateTime createdAt;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateRequest {
        private Integer walletId;
        private Integer orderId;
        private Integer amount;
    }
}
