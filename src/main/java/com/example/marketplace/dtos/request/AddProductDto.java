package com.example.marketplace.dtos.request;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddProductDto {
    @NotBlank
    @Size(max = 50)
    private String name;

    @Size(max = 500)
    private String description;

    @PositiveOrZero
    private Double price;

    @Min(1)
    private Integer quantity;

    @NotEmpty
    private Set<Long> categoryIds;
}
