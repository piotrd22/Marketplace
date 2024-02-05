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
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    private PaymentMethod paymentMethod;

    @Column(nullable = false)
    // MockOnly
    private Long userId;

    private LocalDateTime paymentDate;
}
