package com.example.marketplace.repositories;

import com.example.marketplace.models.Order;
import jakarta.annotation.Nonnull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Nonnull
    Page<Order> findAll(@Nonnull Pageable pageable);

    Page<Order> findByUserId(Long userId, Pageable pageable);
}
