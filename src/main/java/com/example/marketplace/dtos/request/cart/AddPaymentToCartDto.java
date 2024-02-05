package com.example.marketplace.dtos.request.cart;

import com.example.marketplace.enums.PaymentMethod;
import com.example.marketplace.validators.cart.ValidPaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddPaymentToCartDto {
    // Normally, of course, validation and a different payment model are needed, but here only for the test
    @ValidPaymentMethod
    private PaymentMethod paymentMethod;
}
