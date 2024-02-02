package com.example.marketplace.config;

import com.example.marketplace.models.Category;
import com.example.marketplace.models.Product;
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
                Category category = new Category();
                category.setName(categoryName);
                category.setDescription("Desc");
                category = categoryService.addCategory(category);

                Product product = new Product();
                product.setName("Product to '%s'".formatted(categoryName));
                product.setDescription("Desc %s".formatted(categoryName));
                product.setPrice(10.0);
                product.setQuantity(5);
                product.setCategories(Set.of(category));
                productService.addProduct(product);
            }
        };
    }
}
