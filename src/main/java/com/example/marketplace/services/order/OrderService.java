package com.example.marketplace.services.order;

import com.example.marketplace.enums.OrderStatus;
import com.example.marketplace.models.Order;
import com.example.marketplace.models.Address;
import com.example.marketplace.models.Payment;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order getOrder(Long id);
    List<Order> getOrders(Pageable pageable);
    Order saveOrder(Order order);
}
