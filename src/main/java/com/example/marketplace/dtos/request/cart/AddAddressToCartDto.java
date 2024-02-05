package com.example.marketplace.dtos.request.cart;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddAddressToCartDto {
    // Normally, of course, validation and a different address model are needed, but here only for the test
    @NotBlank
    private String addressInfo;
}
