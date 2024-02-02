package com.example.marketplace.services.product;

import com.example.marketplace.dtos.request.AddProductDto;
import com.example.marketplace.dtos.request.ProductFilterDto;
import com.example.marketplace.dtos.request.UpdateProductDto;
import com.example.marketplace.dtos.response.ProductDto;
import com.example.marketplace.exceptions.NotFoundException;
import com.example.marketplace.mappers.ProductMapper;
import com.example.marketplace.models.Product;
import com.example.marketplace.repositories.ProductRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException(id, "Product"));
    }

    @Override
    public List<Product> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable).getContent();
    }

    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
       return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = getProduct(id);
        productRepository.delete(product);
    }

    @Override
    public List<Product> searchProducts(ProductFilterDto filterDto, Pageable pageable) {
        return productRepository.searchProducts(filterDto, pageable).getContent();
    }
}
