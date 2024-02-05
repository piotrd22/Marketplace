package com.example.marketplace.dtos.response;

import com.example.marketplace.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {
    private Long id;
    private PaymentMethod paymentMethod;
    private LocalDateTime paymentDate;
}
