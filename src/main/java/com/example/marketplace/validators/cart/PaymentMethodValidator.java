package com.example.marketplace.validators.cart;

import com.example.marketplace.enums.PaymentMethod;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PaymentMethodValidator implements ConstraintValidator<ValidPaymentMethod, PaymentMethod> {

    @Override
    public void initialize(ValidPaymentMethod constraintAnnotation) {
    }

    @Override
    public boolean isValid(PaymentMethod value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        for (PaymentMethod method : PaymentMethod.values()) {
            if (method == value) {
                return true;
            }
        }
        return false;
    }
}
