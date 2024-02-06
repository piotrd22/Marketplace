package com.example.marketplace.enums;

public enum OrderStatus {
    PAID(1),
    SHIPPED(2),
    DELIVERED(3);

    // We could also add canceled, pending, etc., but I'll leave it like that for now
    private final int value;

    OrderStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}