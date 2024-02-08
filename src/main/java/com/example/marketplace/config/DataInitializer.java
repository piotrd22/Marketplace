package com.example.marketplace.config;

import com.example.marketplace.models.Category;
import com.example.marketplace.models.Product;
import com.example.marketplace.services.category.CategoryService;
import com.example.marketplace.services.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class DataInitializer {

    private final Random random = new Random();
    private final Map<String, List<String>> categoryNames = new HashMap<>();

    {
        categoryNames.put("Games", generateGames());
        categoryNames.put("Books", List.of("Harry Potter", "Lord of the Rings", "Game of Thrones"));
        categoryNames.put("Electronics", List.of("Smartphone", "Laptop", "Headphones", "IPhone", "MacBook", "TV", "Apple Vision Pro", "AirPods"));
        categoryNames.put("Movies", List.of("GoodFellas", "Pulp Fiction", "The Shawshank Redemption", "The Godfather", "The Godfather II", "The Godfather III", "Fight Club", "Django", "12 Angry Man", "Seven", "Inception"));
    }

    @Bean
    public CommandLineRunner dataInitializerRunner(
            @Autowired CategoryService categoryService,
            @Autowired ProductService productService
            ) {
        return args -> {
            for (String categoryName : categoryNames.keySet()) {
                Category category = new Category();
                category.setName(categoryName);
                category.setDescription("Desc");
                category = categoryService.addCategory(category);

                for (int i = 0; i < categoryNames.get(categoryName).size(); i++) {
                    String productName = categoryNames.get(categoryName).get(i);
                    Product product = new Product();
                    product.setName(productName);
                    product.setDescription(generateProductDescription(categoryName, i));
                    product.setPrice(generateRandomPrice());
                    product.setQuantity(generateRandomQuantity());
                    product.setCategories(Set.of(category));
                    productService.addProduct(product);
                }
            }
        };
    }

    private String generateProductDescription(String categoryName, int index) {
        return "%s %s Description".formatted(categoryName, index);
    }

    private int generateRandomQuantity() {
        return random.nextInt(100) + 1;
    }

    private double generateRandomPrice() {
        double price = 20.0 + (random.nextDouble() * (200.0 - 20.0));
        return Math.round(price * 100.0) / 100.0;
    }

    private List<String> generateGames() {
        List<String> games = new ArrayList<>();
        for (int i = 10; i < 24; i++) {
           games.add("Fifa %s".formatted(i));
        }
        return games;
    }
}
