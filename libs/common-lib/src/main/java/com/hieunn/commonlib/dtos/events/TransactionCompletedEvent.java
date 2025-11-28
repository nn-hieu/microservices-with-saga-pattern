package com.hieunn.commonlib.dtos.events;

import com.hieunn.commonlib.enums.status.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionCompletedEvent {
    private Integer transactionId;
    private Integer walletId;
    private Integer orderId;
    private Integer amount;
    private TransactionStatus status;
}
