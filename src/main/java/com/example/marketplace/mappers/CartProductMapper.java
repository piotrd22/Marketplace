package com.example.marketplace.mappers;

import com.example.marketplace.dtos.request.cart.AddProductToCartDto;
import com.example.marketplace.models.CartProduct;
import com.example.marketplace.models.Product;
import com.example.marketplace.services.product.ProductService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public abstract class CartProductMapper {

    @Autowired
    private ProductService productService;

    @Mapping(source = "productId", target = "product", qualifiedByName = "productIdToProduct")
    public abstract CartProduct addProductToCartDtoToCartProduct(AddProductToCartDto dto);

    @Named("productIdToProduct")
    public Product productIdToProduct(Long id) {
        return productService.getProduct(id);
    }
}
