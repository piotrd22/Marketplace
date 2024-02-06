package com.example.marketplace.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    private Long id;
    private List<CartProductDto> cartProducts;
    private PaymentDto payment;
    private AddressDto address;
    private Double cartPrice;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
