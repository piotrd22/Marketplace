package com.example.marketplace.mappers;

import com.example.marketplace.dtos.request.cart.AddProductToCartDto;
import com.example.marketplace.models.CartProduct;
import com.example.marketplace.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public abstract class CartProductMapper {
    @Mapping(source = "productId", target = "product", qualifiedByName = "productIdToProduct")
    public abstract CartProduct addProductToCartDtoToCartProduct(AddProductToCartDto dto);

    @Named("productIdToProduct")
    public Product productIdToProduct(Long id) {
        Product product = new Product();
        product.setId(id);
        return product;
    }
}
