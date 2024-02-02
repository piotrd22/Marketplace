package com.example.marketplace.dtos.request.orderProduct;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddProductToCartDto {
    @NotNull
    private Long productId;

    @NotNull
    @PositiveOrZero
    private Integer quantity;
}
