package com.example.marketplace.dtos.response;

import com.example.marketplace.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private List<OrderProductDto> orderProducts;
    private OrderPaymentDto orderPayment;
    private OrderAddressDto orderAddress;
    private OrderStatus orderStatus;
    private Double price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
