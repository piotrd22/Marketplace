package com.example.marketplace.services.category;

import com.example.marketplace.dtos.request.AddCategoryDto;
import com.example.marketplace.dtos.request.UpdateCategoryDto;
import com.example.marketplace.dtos.response.CategoryDto;

import java.util.List;
import java.util.Set;

public interface CategoryService {
    List<CategoryDto> getAllCategories();
    CategoryDto getCategory(Long id);
    CategoryDto addCategory(AddCategoryDto category);
    CategoryDto updateCategory(Long id, UpdateCategoryDto updatedCategory);
    void deleteCategory(Long id);
}
