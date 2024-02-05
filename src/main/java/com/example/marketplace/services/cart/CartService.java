package com.example.marketplace.services.cart;

import com.example.marketplace.models.Cart;
import com.example.marketplace.models.CartProduct;

public interface CartService {
    Cart addProductToCart(CartProduct cartProduct, Long userId);
    Cart removeProductFromCart(Long id, Long userId);
}
