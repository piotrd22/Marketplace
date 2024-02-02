package com.example.marketplace.services.orderProduct;

import com.example.marketplace.models.Order;
import com.example.marketplace.models.OrderProduct;

public interface OrderProductService {
    Order addProductToCart(OrderProduct orderProduct);
    void removeProductFromCart(Long id);
    OrderProduct getOrderProduct(Long id);
}
