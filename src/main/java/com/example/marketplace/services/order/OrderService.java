package com.example.marketplace.services.order;

import com.example.marketplace.enums.OrderStatus;
import com.example.marketplace.models.Order;
import com.example.marketplace.models.OrderAddress;
import com.example.marketplace.models.OrderPayment;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order getOrder(Long id);
    List<Order> getOrders(Pageable pageable);
    Optional<Order> getOrderByOrderStatus(OrderStatus orderStatus);
    Order saveOrder(Order order);
    Order addAddressToOrder(Long id, OrderAddress orderAddress);
    Order addPaymentToOrder(Long id, OrderPayment orderPayment);

    // Mock only ofc
    Order payForOrder(Long id);
    Order sendOrder(Long id);
    Order deliverOrder(Long id);
}
