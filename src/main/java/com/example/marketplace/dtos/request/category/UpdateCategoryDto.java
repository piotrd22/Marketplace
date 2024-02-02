package com.example.marketplace.dtos.request.category;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCategoryDto {
    @Size(max = 50)
    private String name;

    @Size(max = 500)
    private String description;
}
