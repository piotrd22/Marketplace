package com.example.marketplace.mappers;

import com.example.marketplace.dtos.request.product.AddProductDto;
import com.example.marketplace.dtos.request.product.UpdateProductDto;
import com.example.marketplace.dtos.response.ProductDto;
import com.example.marketplace.models.Category;
import com.example.marketplace.models.Product;
import com.example.marketplace.services.category.CategoryService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private Updater updater;

    public abstract ProductDto productToProductDto(Product product);

    @Mapping(source = "categoryIds", target = "categories", qualifiedByName = "categoryIdToCategory")
    public abstract Product addProductDtoToProduct(AddProductDto addProductDto);

    @Mapping(source = "categoryIds", target = "categories", qualifiedByName = "categoryIdToCategory")
    public abstract Product updateProductDtoToProduct(UpdateProductDto updateProductDto);

    @Mapping(source = "categoryIds", target = "categories", qualifiedByName = "categoryIdToCategory")
    public void updateProduct(Product product, UpdateProductDto dto) {
        updater.update(product, updateProductDtoToProduct(dto));
    }

    @Named("categoryIdToCategory")
    public Category categoryIdToCategory(Long id) {
        return categoryService.getCategory(id);
    }

    @Mapper(
            componentModel = "spring",
            unmappedSourcePolicy = ReportingPolicy.ERROR,
            unmappedTargetPolicy = ReportingPolicy.IGNORE,
            typeConversionPolicy = ReportingPolicy.WARN,
            collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
    )
    interface Updater {
        void update(@MappingTarget Product product, Product dto);
    }
}
