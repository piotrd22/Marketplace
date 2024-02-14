package com.example.marketplace.enums;

import lombok.Getter;

// We could also add canceled, pending, etc., but I'll leave it like that for now
@Getter
public enum OrderStatus {
    PAID(1),
    SHIPPED(2),
    DELIVERED(3);

    private final int value;

    OrderStatus(int value) {
        this.value = value;
    }
}