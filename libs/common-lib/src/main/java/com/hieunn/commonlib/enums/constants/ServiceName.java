package com.hieunn.commonlib.enums.constants;

import lombok.Getter;

@Getter
public enum ServiceName {
    ORDER_SERVICE("order-service"),
    USER_SERVICE("user-service"),
    PAYMENT_SERVICE("payment-service"),
    PRODUCT_SERVICE("product-service");

    private final String value;

    ServiceName(String value) {
        this.value = value;
    }
}