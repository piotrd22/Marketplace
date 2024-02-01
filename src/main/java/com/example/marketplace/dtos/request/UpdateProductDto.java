package com.example.marketplace.dtos.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductDto {
    @Size(max = 50)
    private String name;

    @Size(max = 500)
    private String description;

    @Positive
    private Double price;

    @Positive
    private Integer quantity;

    @Size(min = 1)
    private Set<Long> categoryIds;
}
