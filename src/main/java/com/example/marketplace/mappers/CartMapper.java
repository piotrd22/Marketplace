package com.example.marketplace.mappers;


import com.example.marketplace.dtos.response.CartDto;
import com.example.marketplace.models.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CartProductMapper.class})
public interface CartMapper {
    CartDto cartToCartDto(Cart cart);
}
