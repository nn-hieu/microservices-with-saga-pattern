package com.hieunn.deadletterservice.enums;

import lombok.Getter;

@Getter
public enum RabbitMQQueue {
    DLQ("dlq");

    private final String value;

    RabbitMQQueue(String value) {
        this.value = value;
    }

    public static final String DQL_NAME = "dlq";
}
