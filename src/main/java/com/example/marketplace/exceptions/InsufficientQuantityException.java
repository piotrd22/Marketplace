package com.example.marketplace.exceptions;

public class InsufficientQuantityException extends RuntimeException {
    public InsufficientQuantityException() {
        super("Requested quantity exceeds available quantity");
    }
}
