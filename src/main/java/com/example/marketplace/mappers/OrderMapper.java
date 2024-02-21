package com.example.marketplace.mappers;

import com.example.marketplace.dtos.response.OrderDto;
import com.example.marketplace.models.Order;
import com.example.marketplace.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {OrderProductMapper.class})
public abstract class OrderMapper {

    @Mapping(source = "user", target = "userId", qualifiedByName = "userToUserId")
    public abstract OrderDto orderToOrderDto(Order order);

    @Named("userToUserId")
    public Long userToUserId(User user) {
        return user.getId();
    }
}
