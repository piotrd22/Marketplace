package com.example.marketplace.mappers;

import com.example.marketplace.dtos.response.OrderDto;
import com.example.marketplace.models.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {OrderProductMapper.class})
public interface OrderMapper {
    OrderDto orderToOrderDto(Order order);
}
