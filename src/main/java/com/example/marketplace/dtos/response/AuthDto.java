package com.example.marketplace.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthDto {
    private String tokenType = "Bearer";
    private String token;
    private UserDto user;
}
