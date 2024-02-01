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
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductDto getProduct(Long id) {
        Product product = getProductById(id);
        return productMapper.productToProductDto(product);
    }

    @Override
    public List<ProductDto> getProducts(Pageable pageable) {
        List<Product> products = productRepository.findAll(pageable).getContent();
        return products.stream().map(productMapper::productToProductDto).toList();
    }

    @Override
    public ProductDto addProduct(AddProductDto dto) {
        Product product = productMapper.addProductDtoToProduct(dto);
        product = productRepository.save(product);
        return productMapper.productToProductDto(product);
    }

    @Override
    public ProductDto updateProduct(Long id, UpdateProductDto dto) {
        Product product = getProductById(id);
       productMapper.updateProduct(product, dto);
       product = productRepository.save(product);
       return productMapper.productToProductDto(product);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }

    @Override
    public List<ProductDto> searchProducts(ProductFilterDto filterDto, Pageable pageable) {
        return null;
    }

    private Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException(id, "Product"));
    }
}
