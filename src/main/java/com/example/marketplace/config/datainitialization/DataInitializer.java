package com.example.marketplace.config.datainitialization;

import com.example.marketplace.enums.PaymentMethod;
import com.example.marketplace.models.*;
import com.example.marketplace.services.cart.CartService;
import com.example.marketplace.services.category.CategoryService;
import com.example.marketplace.services.order.OrderService;
import com.example.marketplace.services.product.ProductService;
import com.example.marketplace.services.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import java.net.URL;
import java.util.*;

@Configuration
public class DataInitializer implements CommandLineRunner, Ordered {

    private final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
    private final Random random = new Random();
    private final static int ORDER_NUMBER = 20;
    private final static int CART_PRODUCTS_NUMBER = 4;
    private final YamlDataInitializerProperties yamlDataInitializerProperties;
    private final CategoryService categoryService;
    private final ProductService productService;
    private final CartService cartService;
    private final OrderService orderService;
    private final UserService userService;
    private User user;

    @Value("${initializer.user.username}")
    private String username;

    public DataInitializer(YamlDataInitializerProperties yamlDataInitializerProperties, CategoryService categoryService, ProductService productService, CartService cartService, OrderService orderService, UserService userService) {
        this.yamlDataInitializerProperties = yamlDataInitializerProperties;
        this.categoryService = categoryService;
        this.productService = productService;
        this.cartService = cartService;
        this.orderService = orderService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        user = userService.getUserByUsername(username);
        var categories = yamlDataInitializerProperties.getCategories();

        for (String categoryName : categories.keySet()) {
            Category category = new Category();
            category.setName(categoryName);
            category.setDescription("%s category description".formatted(categoryName));
            category = categoryService.addCategory(category);

            for (int i = 0; i < categories.get(categoryName).size(); i++) {

                String productName = categories.get(categoryName).get(i).get("name");
                Product product = new Product();

                if (categories.get(categoryName).get(i).containsKey("photoUrl")) {
                    product.setPhotoUrl(new URL(categories.get(categoryName).get(i).get("photoUrl")));
                }

                product.setName(productName);
                product.setDescription(generateProductDescription(categoryName, i));
                product.setPrice(generateRandomPrice());
                product.setQuantity(generateRandomQuantity());
                product.setCategories(Set.of(category));
                productService.addProduct(product);
            }
        }

        for (int i = 0; i < ORDER_NUMBER; i++) {
            for (int j = 1; j < CART_PRODUCTS_NUMBER; j++) {
                CartProduct cartProduct = new CartProduct();
                Product product = productService.getProduct((long) i + j);
                cartProduct.setQuantity(j);
                cartProduct.setProduct(product);

                try {
                    cartService.addProductToCart(cartProduct, user.getId());
                } catch (RuntimeException e) {
                    logger.error("DataInitializer Error " + e.getMessage());
                }
            }

            try {
                Address address = generateAddress();
                cartService.saveAddressToCart(user.getId(), address);

                Payment payment = generatePayment();
                cartService.savePaymentToCart(user.getId(), payment);

                orderService.placeOrder(user.getId());
            } catch (RuntimeException e) {
                logger.error("DataInitializer Error " + e.getMessage());
            }
        }
    }

    private String generateProductDescription(String categoryName, int index) {
        return "%s %s description".formatted(categoryName, index);
    }

    private int generateRandomQuantity() {
        return random.nextInt(100) + 1;
    }

    private double generateRandomPrice() {
        double price = 20.0 + (random.nextDouble() * (200.0 - 20.0));
        return Math.round(price * 100.0) / 100.0;
    }

    private Address generateAddress() {
        Address address = new Address();
        address.setUserId(user.getId());
        address.setAddress("ul. Jana 8");
        address.setCity("Gdansk");
        address.setCountry("Poland");
        address.setState("Pomerania");
        address.setZipCode("80-414");
        return address;
    }

    private Payment generatePayment() {
        Payment payment = new Payment();
        payment.setPaymentMethod(PaymentMethod.CARD);
        payment.setUserId(user.getId());
        return payment;
    }

    @Override
    public int getOrder() {
        return 3;
    }
}
