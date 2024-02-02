package com.example.marketplace.enums;

public enum OrderStatus {
    CART(1),
    PAID(2),
    SHIPPED(3),
    DELIVERED(4);

    // We could also add canceled, pending, etc., but I'll leave it like that for now
    private final int value;

    OrderStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}