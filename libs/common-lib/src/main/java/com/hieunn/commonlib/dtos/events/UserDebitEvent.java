package com.hieunn.commonlib.dtos.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDebitEvent {
    private Integer orderId;
    private Integer userId;
    private Integer totalAmount;
    private Boolean isSuccess;
}
