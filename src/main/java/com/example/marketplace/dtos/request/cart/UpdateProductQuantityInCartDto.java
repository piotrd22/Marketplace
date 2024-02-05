package com.example.marketplace.dtos.request.cart;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductQuantityInCartDto {
    @Positive
    private int newQuantity;
}
