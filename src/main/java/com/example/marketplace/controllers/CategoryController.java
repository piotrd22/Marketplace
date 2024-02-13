package com.example.marketplace.controllers;

import com.example.marketplace.dtos.request.category.AddCategoryDto;
import com.example.marketplace.dtos.request.category.UpdateCategoryDto;
import com.example.marketplace.dtos.response.CategoryDto;
import com.example.marketplace.mappers.CategoryMapper;
import com.example.marketplace.models.Category;
import com.example.marketplace.services.category.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController extends AbstractControllerBase {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    public CategoryController(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        logger.info("Inside: CategoryController -> getAllCategories()...");
        List<Category> categories = categoryService.getAllCategories();
        List<CategoryDto> categoryDtos = categories.stream().map(categoryMapper::categoryToCategoryDto).toList();
        return ResponseEntity.ok().body(categoryDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Long id) {
        logger.info("Inside: CategoryController -> getCategory()...");
        Category category = categoryService.getCategory(id);
        return ResponseEntity.ok().body(categoryMapper.categoryToCategoryDto(category));
    }

    @PostMapping
    public ResponseEntity<CategoryDto> addCategory(@RequestBody @Valid AddCategoryDto dto, HttpServletRequest request) {
        logger.info("Inside: CategoryController -> addCategory()...");
        Category category = categoryMapper.addCategoryDtoToCategory(dto);
        category = categoryService.addCategory(category);
        URI location = getURILocationFromRequest(category.getId(), request);
        return ResponseEntity.created(location).body(categoryMapper.categoryToCategoryDto(category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody @Valid UpdateCategoryDto dto, @PathVariable Long id) {
        logger.info("Inside: CategoryController -> updateCategory()...");
        Category category = categoryService.getCategory(id);
        categoryMapper.updateCategory(category, dto);
        category = categoryService.updateCategory(category);
        return ResponseEntity.ok().body(categoryMapper.categoryToCategoryDto(category));
    }

    // If a product has a category assigned to it, it cannot be removed
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        logger.info("Inside: CategoryController -> deleteCategory()...");
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
