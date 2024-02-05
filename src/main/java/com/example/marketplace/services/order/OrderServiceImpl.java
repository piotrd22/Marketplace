package com.example.marketplace.services.order;

import com.example.marketplace.enums.OrderStatus;
import com.example.marketplace.exceptions.NotFoundException;
import com.example.marketplace.models.Address;
import com.example.marketplace.models.Order;
import com.example.marketplace.models.Payment;
import com.example.marketplace.repositories.OrderRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        return orderRepository.findById(id).orElseThrow(() -> new NotFoundException(id, "Order"));
    }

    @Override
    public List<Order> getOrders(Pageable pageable) {
        return orderRepository.findAll(pageable).getContent();
    }

    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }
}
