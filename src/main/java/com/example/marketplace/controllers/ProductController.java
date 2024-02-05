package com.example.marketplace.controllers;

import com.example.marketplace.dtos.request.product.AddProductDto;
import com.example.marketplace.dtos.request.product.ProductFilterDto;
import com.example.marketplace.dtos.request.product.UpdateKeyProductValuesDto;
import com.example.marketplace.dtos.request.product.UpdateProductDto;
import com.example.marketplace.dtos.response.ProductDto;
import com.example.marketplace.mappers.ProductMapper;
import com.example.marketplace.models.Product;
import com.example.marketplace.services.product.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController extends AbstractControllerBase {

    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts(Pageable pageable) {
        logger.info("Inside: ProductController -> getProducts()...");
        List<Product> products = productService.getProducts(pageable);
        List<ProductDto> productDtos = products.stream().map(productMapper::productToProductDto).toList();
        return ResponseEntity.ok().body(productDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) {
        logger.info("Inside: ProductController -> getProduct()...");
        Product product = productService.getProduct(id);
        return ResponseEntity.ok().body(productMapper.productToProductDto(product));
    }

    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@RequestBody @Valid AddProductDto dto, HttpServletRequest request){
        logger.info("Inside: ProductController -> addProduct()...");
        Product product = productMapper.addProductDtoToProduct(dto);
        product = productService.addProduct(product);
        URI location = getURILocationFromRequest(product.getId(), request);
        return ResponseEntity.created(location).body(productMapper.productToProductDto(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        logger.info("Inside: ProductController -> deleteProduct()...");
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody @Valid UpdateProductDto dto) {
        logger.info("Inside: ProductController -> updateProduct()...");
        Product product = productService.getProduct(id);
        productMapper.updateProduct(product, dto);
        product = productService.updateProduct(product);
        return ResponseEntity.ok().body(productMapper.productToProductDto(product));
    }

    @PutMapping("/{id}/key")
    // With this method, we update very important data, such as price, to preserve the history of such important things in old orders.
    public ResponseEntity<ProductDto> updateKeyProductValues(@PathVariable Long id, @RequestBody @Valid UpdateKeyProductValuesDto dto) {
        logger.info("Inside: ProductController -> updateKeyProductValues()...");
        Product product = productService.getProduct(id);
        Product newProduct = productMapper.mapProduct(product);
        productMapper.updateProductKeyValues(newProduct, dto);
        product = productService.updateKeyProductValues(product.getId(), newProduct);
        return ResponseEntity.ok().body(productMapper.productToProductDto(product));
    }


    @PostMapping("/search")
    public ResponseEntity<List<ProductDto>> searchProducts(@RequestBody @Valid ProductFilterDto filterDto, Pageable pageable) {
        logger.info("Inside: ProductController -> searchProducts()...");
        List<Product> products = productService.searchProducts(filterDto, pageable);
        List<ProductDto> productDtos = products.stream().map(productMapper::productToProductDto).toList();
        return ResponseEntity.ok().body(productDtos);
    }
}
