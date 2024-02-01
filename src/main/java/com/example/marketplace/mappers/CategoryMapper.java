package com.example.marketplace.mappers;

import com.example.marketplace.dtos.request.AddCategoryDto;
import com.example.marketplace.dtos.request.UpdateCategoryDto;
import com.example.marketplace.dtos.response.CategoryDto;
import com.example.marketplace.models.Category;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class CategoryMapper {

    @Autowired
    private Updater updater;

    public abstract CategoryDto categoryToCategoryDto(Category category);
    public abstract Category addCategoryDtoToCategory(AddCategoryDto addCategoryDto);

    public void updateCategory(Category category, UpdateCategoryDto updateCategoryDto) {
        updater.update(category, updateCategoryDto);
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
        void update(@MappingTarget Category category, UpdateCategoryDto updateCategoryDto);
    }
}
