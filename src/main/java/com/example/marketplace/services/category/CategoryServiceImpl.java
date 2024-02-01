package com.example.marketplace.services.category;

import com.example.marketplace.dtos.request.AddCategoryDto;
import com.example.marketplace.dtos.request.UpdateCategoryDto;
import com.example.marketplace.dtos.response.CategoryDto;
import com.example.marketplace.exceptions.AlreadyExistsException;
import com.example.marketplace.exceptions.EntityInUseException;
import com.example.marketplace.exceptions.NotFoundException;
import com.example.marketplace.mappers.CategoryMapper;
import com.example.marketplace.models.Category;
import com.example.marketplace.repositories.CategoryRepository;
import com.example.marketplace.services.product.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(categoryMapper::categoryToCategoryDto).toList();
    }

    @Override
    public CategoryDto getCategory(Long id) {
        Category category = getCategoryById(id);
        return categoryMapper.categoryToCategoryDto(category);
    }

    @Override
    public CategoryDto addCategory(AddCategoryDto dto) {
        if (categoryRepository.existsByNameIgnoreCase(dto.getName())) {
            throw new AlreadyExistsException("Category with name '%s' already exists".formatted(dto.getName()));
        }

        Category category = categoryMapper.addCategoryDtoToCategory(dto);
        category = categoryRepository.save(category);
        return categoryMapper.categoryToCategoryDto(category);
    }

    @Override
    public CategoryDto updateCategory(Long id, UpdateCategoryDto dto) {
        Category category = getCategoryById(id);
        categoryMapper.updateCategory(category, dto);
        category = categoryRepository.save(category);
        return categoryMapper.categoryToCategoryDto(category);
    }

    @Override
    public void deleteCategory(Long id) {
        if (categoryRepository.existsProductInCategory(id)) {
            throw new EntityInUseException("Category with id '%s' is in use by products".formatted(id));
        }

        Category category = getCategoryById(id);
        categoryRepository.delete(category);
    }

    private Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new NotFoundException(id, "Category"));
    }
}
