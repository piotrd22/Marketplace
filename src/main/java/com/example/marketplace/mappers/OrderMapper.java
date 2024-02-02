package com.example.marketplace.mappers;

import com.example.marketplace.dtos.response.OrderDto;
import com.example.marketplace.models.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {OrderProductMapper.class})
public abstract class OrderMapper {

    @Mapping(target = "price", expression = "java(calculatePrice(order))")
    public abstract OrderDto orderToOrderDto(Order order);

    public Double calculatePrice(Order order) {
        return order.getOrderProducts().stream()
                .mapToDouble(orderProduct -> orderProduct.getProduct().getPrice() * orderProduct.getQuantity())
                .sum();
    }
}
