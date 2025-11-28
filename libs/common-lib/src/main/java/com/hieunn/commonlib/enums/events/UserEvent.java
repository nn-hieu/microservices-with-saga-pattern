package com.hieunn.commonlib.enums.events;

import lombok.Getter;

@Getter
public enum UserEvent {
    USER_DEBIT_FAILED("user.debit.failed"),
    USER_DEBIT_SUCCEEDED("user.debit.succeeded"),;

    private final String value;

    UserEvent(String value) {
        this.value = value;
    }

    public static UserEvent fromValue(String value) {
        for (UserEvent e : values()) {
            if (e.value.equalsIgnoreCase(value)) return e;
        }
        return null;
    }

    public static boolean isUserEvent(String value) {
        return fromValue(value) != null;
    }
}