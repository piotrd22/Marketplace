package com.example.marketplace.mappers;

import com.example.marketplace.dtos.request.cart.AddAddressToCartDto;
import com.example.marketplace.models.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    Address addAddressToCartDtoToAddress(AddAddressToCartDto dto);
}
