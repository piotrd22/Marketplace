package com.example.marketplace.services.product;

import com.example.marketplace.dtos.filter.ProductFilterDto;
import com.example.marketplace.exceptions.AlreadyExistsException;
import com.example.marketplace.exceptions.NotFoundException;
import com.example.marketplace.models.Category;
import com.example.marketplace.models.Product;
import com.example.marketplace.repositories.ProductRepository;
import com.example.marketplace.services.category.CategoryService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException(id, "Product"));
    }

    @Override
    public List<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable).getContent();
    }

    @Override
    public Product addProduct(Product product) {
        if (productRepository.existsByNameIgnoreCase(product.getName())) {
            throw new AlreadyExistsException("Product with name '%s' already exists".formatted(product.getName()));
        }

        List<Long> categoryIds = product.getCategories().stream().map(Category::getId).toList();
        if (categoryService.allCategoriesExist(categoryIds)) {
            throw new NotFoundException("Some categories do not exist: " + categoryIds);
        }

        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product updatedProduct) {
        return productRepository.save(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = getProduct(id);
        productRepository.delete(product);
    }

    @Override
    public List<Product> searchProducts(ProductFilterDto filterDto, Pageable pageable) {
        return null;
    }
}
