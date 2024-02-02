package com.example.marketplace.services.order;

import com.example.marketplace.enums.OrderStatus;
import com.example.marketplace.models.Order;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order getOrder(Long id);
    List<Order> getOrders(Pageable pageable);
    Optional<Order> getOrderByOrderStatus(OrderStatus orderStatus);
    Order saveOrder(Order order);
    void deleteCartOrder(Long id);
}
