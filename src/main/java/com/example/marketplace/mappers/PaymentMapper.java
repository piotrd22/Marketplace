package com.example.marketplace.mappers;

import com.example.marketplace.dtos.request.cart.AddPaymentToCartDto;
import com.example.marketplace.dtos.response.PaymentDto;
import com.example.marketplace.models.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentDto paymentDtoToPayment(PaymentDto paymentDto);
    Payment addPaymentToCartDtoToPayment(AddPaymentToCartDto addPaymentToCartDto);
}
