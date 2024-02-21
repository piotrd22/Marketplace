package com.example.marketplace.mappers;

import com.example.marketplace.dtos.response.CartDto;
import com.example.marketplace.models.Cart;
import com.example.marketplace.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {CartProductMapper.class})
public abstract class CartMapper {

    @Mapping(source = "user", target = "userId", qualifiedByName = "userToUserId")
    public abstract CartDto cartToCartDto(Cart cart);

    @Named("userToUserId")
    public Long userToUserId(User user) {
        return user.getId();
    }
}
