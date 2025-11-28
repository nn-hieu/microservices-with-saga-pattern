package com.hieunn.userservice.enums;

import lombok.Getter;

@Getter
public enum RabbitMQQueue {
    ORDER_CREATED_SUCCEEDED_QUEUE("order-created-succeeded.queue.user-service");

    private final String value;

    RabbitMQQueue(String value) {
        this.value = value;
    }

    public static final String ORDER_CREATED_SUCCEEDED_QUEUE_NAME = "order-created-succeeded.queue.user-service";
}
