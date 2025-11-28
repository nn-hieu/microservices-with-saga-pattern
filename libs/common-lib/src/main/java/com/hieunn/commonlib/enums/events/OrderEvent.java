package com.hieunn.commonlib.enums.events;

import lombok.Getter;

@Getter
public enum OrderEvent {
    ORDER_CREATED_SUCCEEDED("order.created.succeeded");

    private final String value;

    OrderEvent(String value) {
        this.value = value;
    }

    public static OrderEvent fromValue(String value) {
        for (OrderEvent e : values()) {
            if (e.value.equalsIgnoreCase(value)) return e;
        }
        return null;
    }

    public static boolean isOrderEvent(String value) {
        return fromValue(value) != null;
    }
}
