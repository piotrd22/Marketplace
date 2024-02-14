package com.example.marketplace.enums;

import lombok.Getter;

@Getter
public enum PaymentMethod {
    CARD(1),
    TRANSFER(2);

    private final int value;

    PaymentMethod(int value) {
        this.value = value;
    }
}
