package com.example.marketplace.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(Long id, String modelName) {
        super("%s with id '%s' not found".formatted(modelName, id));
    }
}
