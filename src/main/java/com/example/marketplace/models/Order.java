package com.example.marketplace.models;

import com.example.marketplace.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<OrderProduct> orderProducts;

    @OneToOne(cascade = CascadeType.ALL)
    private Payment payment;

    @OneToOne(cascade = CascadeType.ALL)
    private OrderAddress orderAddress;

    @Enumerated(EnumType.ORDINAL)
    private OrderStatus orderStatus;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        updatedAt = createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
