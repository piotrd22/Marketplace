package com.example.marketplace.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductFilterDto {
    private String name;
    private String description;
    private Set<Long> categoryIds;
    private double maxPrice;
    private double minPrice;
}
