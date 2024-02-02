package com.example.marketplace.enums;

public enum PaymentMethod {
    CARD(1),
    TRANSFER(2);

    private final int value;

    PaymentMethod(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
