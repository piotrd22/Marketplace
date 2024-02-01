package com.example.marketplace.controllers;

import com.example.marketplace.dtos.request.AddProductDto;
import com.example.marketplace.dtos.request.ProductFilterDto;
import com.example.marketplace.dtos.request.UpdateProductDto;
import com.example.marketplace.dtos.response.ProductDto;
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

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts(Pageable pageable) {
        logger.info("Inside: ProductController -> getProducts()...");
        List<ProductDto> products = productService.getProducts(pageable);
        return ResponseEntity.ok().body(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) {
        logger.info("Inside: ProductController -> getProduct()...");
        ProductDto product = productService.getProduct(id);
        return ResponseEntity.ok().body(product);
    }

    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@RequestBody @Valid AddProductDto dto, HttpServletRequest request){
        logger.info("Inside: ProductController -> addProduct()...");
        ProductDto product = productService.addProduct(dto);
        URI location = getURILocationFromRequest(product.getId(), request);
        return ResponseEntity.created(location).body(product);
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
        ProductDto product = productService.updateProduct(id, dto);
        return ResponseEntity.ok().body(product);
    }

    @PostMapping("/search")
    public ResponseEntity<List<ProductDto>> searchProducts(@RequestBody @Valid ProductFilterDto filterDto, Pageable pageable) {
        logger.info("Inside: ProductController -> searchProducts()...");
        List<ProductDto> products = productService.searchProducts(filterDto, pageable);
        return ResponseEntity.ok().body(products);
    }
}
