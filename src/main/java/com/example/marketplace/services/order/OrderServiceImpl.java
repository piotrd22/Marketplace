package com.example.marketplace.services.order;

import com.example.marketplace.enums.OrderStatus;
import com.example.marketplace.exceptions.NotFoundException;
import com.example.marketplace.models.Order;
import com.example.marketplace.models.OrderAddress;
import com.example.marketplace.models.OrderPayment;
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
    public Optional<Order> getOrderByOrderStatus(OrderStatus orderStatus) {
        return orderRepository.findByOrderStatus(orderStatus);
    }

    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order addAddressToOrder(Long id, OrderAddress orderAddress) {
        return null;
    }

    @Override
    public Order addPaymentToOrder(Long id, OrderPayment orderPayment) {
        return null;
    }

    @Override
    public Order payForOrder(Long id) {
        return null;
    }

    @Override
    public Order sendOrder(Long id) {
        return null;
    }

    @Override
    public Order deliverOrder(Long id) {
        return null;
    }
}
