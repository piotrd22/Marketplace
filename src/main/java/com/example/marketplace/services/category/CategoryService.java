package com.example.marketplace.services.category;

import com.example.marketplace.models.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    Category getCategory(Long id);
    Category addCategory(Category category);
    Category updateCategory(Category updatedCategory);
    void deleteCategory(Long id);
    boolean allCategoriesExist(List<Long> ids);
}
