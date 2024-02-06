package com.example.marketplace.dtos.request.product;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductDto {
    @Size(max = 50)
    private String name;

    @Size(max = 500)
    private String description;

    @Min(0)
    private Integer quantity;

    @Size(min = 1)
    private Set<Long> categoryIds;

    @Min(0)
    private Double price;

    private URL photoUrl;
}
