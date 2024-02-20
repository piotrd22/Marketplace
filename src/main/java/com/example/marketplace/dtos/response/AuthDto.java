package com.example.marketplace.dtos.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class AuthDto {
    private String tokenType = "Bearer";
    private String token;
    private UserDto user;
}
