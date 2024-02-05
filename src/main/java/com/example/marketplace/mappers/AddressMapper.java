package com.example.marketplace.mappers;

import com.example.marketplace.dtos.request.cart.AddAddressToCartDto;
import com.example.marketplace.dtos.response.AddressDto;
import com.example.marketplace.models.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressDto addressToAddressDto(Address address);
    Address addAddressToCartDtoToAddress(AddAddressToCartDto dto);
}
