package com.example.marketplace.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    private Long id;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private Long userId;
    private LocalDateTime createdAt;
}
