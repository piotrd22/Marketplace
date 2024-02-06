package com.example.marketplace.services.order;

import com.example.marketplace.models.Order;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    Order placeOrder(Long userId);
    Order getOrder(Long id);
    List<Order> getOrders(Pageable pageable);
    List<Order> getOrdersByUserId(Long userId, Pageable pageable);
}
