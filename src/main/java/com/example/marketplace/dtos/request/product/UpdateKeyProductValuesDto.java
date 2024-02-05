package com.example.marketplace.dtos.request.product;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateKeyProductValuesDto {
    @Min(0)
    private Double price;
}
