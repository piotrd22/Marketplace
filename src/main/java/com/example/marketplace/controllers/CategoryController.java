package com.example.marketplace.controllers;

import com.example.marketplace.dtos.request.AddCategoryDto;
import com.example.marketplace.dtos.request.UpdateCategoryDto;
import com.example.marketplace.dtos.response.CategoryDto;
import com.example.marketplace.mappers.CategoryMapper;
import com.example.marketplace.models.Category;
import com.example.marketplace.services.category.CategoryService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
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
    public ResponseEntity<CategoryDto> addCategory(@RequestBody @Valid AddCategoryDto dto) {
        logger.info("Inside: CategoryController -> addCategory()...");
        Category category = categoryMapper.addCategoryDtoToCategory(dto);
        category = categoryService.addCategory(category);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(category.getId())
                .toUri();

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        logger.info("Inside: CategoryController -> deleteCategory()...");
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
