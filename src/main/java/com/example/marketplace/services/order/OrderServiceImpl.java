package com.example.marketplace.services.order;

import com.example.marketplace.enums.OrderStatus;
import com.example.marketplace.models.Order;
import com.example.marketplace.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order getOrder(Long id) {
        return null;
    }

    @Override
    public List<Order> getOrders(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<Order> getOrderByOrderStatus(OrderStatus orderStatus) {
        return orderRepository.findByOrderStatus(orderStatus);
    }

    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public void deleteCartOrder(Long id) {

    }
}
