package com.example.marketplace.config;

import com.example.marketplace.dtos.request.AddCategoryDto;
import com.example.marketplace.dtos.request.AddProductDto;
import com.example.marketplace.dtos.response.CategoryDto;
import com.example.marketplace.services.category.CategoryService;
import com.example.marketplace.services.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Set;

@Configuration
public class DataInitializer {

    List<String> categoryNames = List.of("Games", "Books", "Electronics", "Music");

    @Bean
    public CommandLineRunner dataInitializerRunner(
            @Autowired CategoryService categoryService,
            @Autowired ProductService productService
            ) {
        return args -> {
            for (String categoryName : categoryNames) {
                CategoryDto categoryDto = categoryService.addCategory(new AddCategoryDto(categoryName, "Desc"));
                productService.addProduct(new AddProductDto("Product to '%s'".formatted(categoryName), "Desc1", 10.0, 5, Set.of(categoryDto.getId())));
            }
        };
    }
}
