package com.example.marketplace.services.cart;

import com.example.marketplace.models.*;

public interface CartService {
    Cart getCartByUserId(Long userId);
    Cart addProductToCart(CartProduct cartProduct, User user);
    Cart removeProductFromCart(Long id, Long userId);
    Cart updateProductQuantityInCart(Long userId, Long cartProductId, int newQuantity);
    Cart saveAddressToCart(Long userId, Address address);
    Cart savePaymentToCart(Long userId, Payment payment);
    void deleteCart(Long userId);
    int getCartProductLengthByUserId(Long userId);
}
