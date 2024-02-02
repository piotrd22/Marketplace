package com.example.marketplace.enums;

public enum OrderStatus {
    CART(1),
    PENDING(2),
    PAID(3),
    SHIPPED(4),
    DELIVERED(5);

    // We could also add canceled, but I'll leave it like that for now
    private final int value;

    OrderStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}