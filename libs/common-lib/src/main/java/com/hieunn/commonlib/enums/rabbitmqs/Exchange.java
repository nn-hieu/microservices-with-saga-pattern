package com.hieunn.commonlib.enums.rabbitmqs;

import lombok.Getter;

@Getter
public enum Exchange {
    ORDER_EXCHANGE("order.exchange"),
    USER_EXCHANGE("user.exchange"),
    PAYMENT_EXCHANGE("payment.exchange"),
    DEAD_LETTER_EXCHANGE("dead-letter.exchange"),;

    private final String value;

    Exchange(String value) {
        this.value = value;
    }
}
