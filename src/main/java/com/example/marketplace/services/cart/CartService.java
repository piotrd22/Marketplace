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
    Cart addAddressToCart(Long userId, Address address);
    Cart addPaymentToCart(Long userId, Payment payment);
    Cart updateAddressInCart(Long userId, Address address);
    Cart updatePaymentInCart(Long userId, Payment payment);
    void deleteCart(Long userId);
}
