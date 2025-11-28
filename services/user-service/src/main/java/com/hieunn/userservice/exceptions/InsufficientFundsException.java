package com.hieunn.userservice.exceptions;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException() {
        super("Insufficient funds");
    }

    public InsufficientFundsException(String message) {
        super(message);
    }
}
