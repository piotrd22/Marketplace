package com.example.marketplace.dtos.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductFilterDto {
    @Size(max = 50)
    private String name;

    @Size(max = 50)
    private String description;

    @Size(min = 1)
    private Set<Long> categoryIds;

    @Min(0)
    private Double maxPrice;

    @Min(0)
    private Double minPrice;

    @AssertTrue(message = "maxPrice must be greater than minPrice")
    private boolean isValidPriceRange() {
        if (maxPrice != null && minPrice != null) {
            return maxPrice >= minPrice;
        }
        return true;
    }
}
