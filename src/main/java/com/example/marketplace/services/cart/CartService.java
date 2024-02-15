package com.example.marketplace.services.cart;

import com.example.marketplace.models.Address;
import com.example.marketplace.models.Cart;
import com.example.marketplace.models.CartProduct;
import com.example.marketplace.models.Payment;

public interface CartService {
    Cart getCartByUserId(Long userId);
    Cart addProductToCart(CartProduct cartProduct, Long userId);
    Cart removeProductFromCart(Long id, Long userId);
    Cart updateProductQuantityInCart(Long userId, Long cartProductId, int newQuantity);
    Cart saveAddressToCart(Long userId, Address address);
    Cart savePaymentToCart(Long userId, Payment payment);
    void deleteCart(Long userId);
    int getCartProductLengthByUserId(Long userId);
}
