package com.example.marketplace.dtos.request;

import jakarta.validation.constraints.AssertTrue;
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
    private Double maxPrice;
    private Double minPrice;

    @AssertTrue(message = "maxPrice must be greater than minPrice")
    private boolean isValidPriceRange() {
        if (maxPrice != null && minPrice != null) {
            return maxPrice >= minPrice;
        }
        return true;
    }
}
