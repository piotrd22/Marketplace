package com.example.marketplace.services.product;

import com.example.marketplace.dtos.filter.ProductFilterDto;
import com.example.marketplace.models.Product;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Product getProduct(Long id);
    List<Product> getAllProducts(Pageable pageable);
    Product addProduct(Product product);
    Product updateProduct(Product updatedProduct);
    void deleteProduct(Long id);
    List<Product> searchProducts(ProductFilterDto filterDto, Pageable pageable);
}
