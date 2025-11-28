package com.hieunn.paymentservice.enums;

import lombok.Getter;

@Getter
public enum RabbitMQQueue {
    USER_DEBIT_SUCCEEDED_QUEUE("user-debit-succeeded.queue.payment-service"),
    USER_DEBIT_FAILED_QUEUE("user-debit-failed.queue.payment-service"),
    ORDER_CREATED_SUCCEEDED_QUEUE("order-created-succeeded.queue.payment-service");

    private final String value;

    RabbitMQQueue(String value) {
        this.value = value;
    }

    public static final String USER_DEBIT_SUCCEEDED_QUEUE_NAME = "user-debit-succeeded.queue.payment-service";
    public static final String USER_DEBIT_FAILED_QUEUE_NAME = "user-debit-failed.queue.payment-service";
    public static final String ORDER_CREATED_SUCCEEDED_QUEUE_NAME = "order-created-succeeded.queue.payment-service";
}
