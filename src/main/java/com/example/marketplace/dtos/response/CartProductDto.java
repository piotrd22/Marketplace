package com.example.marketplace.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartProductDto {
    private Long id;
    private ProductDto product;
    private Integer quantity;
    private Double productPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
