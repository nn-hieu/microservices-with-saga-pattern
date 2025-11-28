package com.hieunn.commonlib.enums.events;

import lombok.Getter;

@Getter
public enum PaymentEvent {
    TRANSACTION_COMPLETED("transaction.completed");

    private final String value;

    PaymentEvent(String value) {
        this.value = value;
    }

    public static PaymentEvent fromValue(String value) {
        for (PaymentEvent e : values()) {
            if (e.value.equalsIgnoreCase(value)) return e;
        }
        return null;
    }

    public static boolean isPaymentEvent(String value) {
        return fromValue(value) != null;
    }
}
