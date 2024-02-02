package com.example.marketplace.models;

import com.example.marketplace.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "payment")
    private Order order;

    @Enumerated(EnumType.ORDINAL)
    private PaymentMethod paymentMethod;

    private LocalDateTime paymentDate;
}
