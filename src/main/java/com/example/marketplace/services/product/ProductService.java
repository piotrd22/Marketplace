package com.example.marketplace.services.product;

import com.example.marketplace.dtos.request.product.ProductFilterDto;
import com.example.marketplace.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Product getProduct(Long id);
    List<Product> getProducts(Pageable pageable);
    Product addProduct(Product product);
    Product updateProduct(Product product);
    void deleteProduct(Long id);
    Page<Product> searchProducts(ProductFilterDto filterDto, Pageable pageable);
}
