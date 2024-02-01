package com.example.marketplace.services.product;

import com.example.marketplace.dtos.request.AddProductDto;
import com.example.marketplace.dtos.request.ProductFilterDto;
import com.example.marketplace.dtos.request.UpdateProductDto;
import com.example.marketplace.dtos.response.ProductDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductDto getProduct(Long id);
    List<ProductDto> getProducts(Pageable pageable);
    ProductDto addProduct(AddProductDto addProductDto);
    ProductDto updateProduct(Long id, UpdateProductDto updateProductDto);
    void deleteProduct(Long id);
    List<ProductDto> searchProducts(ProductFilterDto filterDto, Pageable pageable);
}
