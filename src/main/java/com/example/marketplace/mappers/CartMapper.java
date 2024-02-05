package com.example.marketplace.mappers;


import com.example.marketplace.dtos.response.CartDto;
import com.example.marketplace.models.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CartProductMapper.class})
public abstract class CartMapper {

    @Mapping(target = "price", expression = "java(calculatePrice(cart))")
    public abstract CartDto cartToCartDto(Cart cart);

    public Double calculatePrice(Cart cart) {
        return cart.getCartProducts().stream()
                .mapToDouble(cartProduct -> cartProduct.getProduct().getPrice() * cartProduct.getQuantity())
                .sum();
    }
}
