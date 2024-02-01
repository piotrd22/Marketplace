package com.example.marketplace.controllers;

import com.example.marketplace.dtos.request.AddCategoryDto;
import com.example.marketplace.dtos.request.UpdateCategoryDto;
import com.example.marketplace.dtos.response.CategoryDto;
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

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        logger.info("Inside: CategoryController -> getAllCategories()...");
        List<CategoryDto> categories = categoryService.getAllCategories();
        return ResponseEntity.ok().body(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Long id) {
        logger.info("Inside: CategoryController -> getCategory()...");
        CategoryDto category = categoryService.getCategory(id);
        return ResponseEntity.ok().body(category);
    }

    @PostMapping
    public ResponseEntity<CategoryDto> addCategory(@RequestBody @Valid AddCategoryDto dto, HttpServletRequest request) {
        logger.info("Inside: CategoryController -> addCategory()...");
        CategoryDto category = categoryService.addCategory(dto);
        URI location = getURILocationFromRequest(category.getId(), request);
        return ResponseEntity.created(location).body(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody @Valid UpdateCategoryDto dto, @PathVariable Long id) {
        logger.info("Inside: CategoryController -> updateCategory()...");
        CategoryDto category = categoryService.updateCategory(id, dto);
        return ResponseEntity.ok().body(category);
    }

    // If a product has a category assigned to it, it cannot be removed
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        logger.info("Inside: CategoryController -> deleteCategory()...");
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
