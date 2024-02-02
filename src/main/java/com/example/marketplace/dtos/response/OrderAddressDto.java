package com.example.marketplace.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderAddressDto {
    private Long id;
    private String addressInfo;
    private LocalDateTime createdAt;
}
