package com.hieunn.orderservice.enums;

import lombok.Getter;

@Getter
public enum RabbitMQQueue {
    USER_DEBIT_FAILED_QUEUE("user-debit-failed.queue.order-service"),
    TRANSACTION_COMPLETED_QUEUE("transaction-completed.queue.order-service"),;

    private final String value;

    RabbitMQQueue(String value) {
        this.value = value;
    }

    public static final String USER_DEBIT_FAILED_QUEUE_NAME = "user-debit-failed.queue.order-service";
    public static final String TRANSACTION_COMPLETED_QUEUE_NAME = "transaction-completed.queue.order-service";
}
