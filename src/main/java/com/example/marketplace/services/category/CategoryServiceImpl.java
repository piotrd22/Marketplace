package com.example.marketplace.services.category;

import com.example.marketplace.exceptions.AlreadyExistsException;
import com.example.marketplace.exceptions.EntityInUseException;
import com.example.marketplace.exceptions.NotFoundException;
import com.example.marketplace.models.Category;
import com.example.marketplace.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategory(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new NotFoundException(id, "Category"));
    }

    @Override
    public Category addCategory(Category category) {
        if (categoryRepository.existsByNameIgnoreCase(category.getName())) {
            throw new AlreadyExistsException("Category with name '%s' already exists".formatted(category.getName()));
        }

        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        if (categoryRepository.existsProductInCategory(id)) {
            throw new EntityInUseException("Category with id '%s' is in use by products".formatted(id));
        }

        Category category = getCategory(id);
        categoryRepository.delete(category);
    }
}
