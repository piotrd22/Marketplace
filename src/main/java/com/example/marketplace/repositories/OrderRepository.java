package com.example.marketplace.repositories;

import com.example.marketplace.enums.OrderStatus;
import com.example.marketplace.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderStatus(OrderStatus orderStatus);
}
