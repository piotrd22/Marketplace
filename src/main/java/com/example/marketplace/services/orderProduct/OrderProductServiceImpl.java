package com.example.marketplace.services.orderProduct;

import com.example.marketplace.enums.OrderStatus;
import com.example.marketplace.exceptions.InsufficientQuantityException;
import com.example.marketplace.exceptions.NotFoundException;
import com.example.marketplace.models.Order;
import com.example.marketplace.models.OrderProduct;
import com.example.marketplace.models.Product;
import com.example.marketplace.repositories.OrderProductRepository;
import com.example.marketplace.services.order.OrderService;
import com.example.marketplace.services.product.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderProductServiceImpl implements OrderProductService {

    private final OrderProductRepository orderProductRepository;
    private final OrderService orderService;
    private final ProductService productService;

    public OrderProductServiceImpl(OrderProductRepository orderProductRepository, OrderService orderService, ProductService productService) {
        this.orderProductRepository = orderProductRepository;
        this.orderService = orderService;
        this.productService = productService;
    }

    @Override
    public OrderProduct getOrderProduct(Long id) {
        return orderProductRepository.findById(id).orElseThrow(() -> new NotFoundException(id, "OrderProduct"));
    }

    @Override
    @Transactional
    public Order addProductToCart(OrderProduct orderProduct) {
        Product product = productService.getProduct(orderProduct.getProduct().getId());
        Optional<Order> cartOrder = orderService.getOrderByOrderStatus(OrderStatus.CART);

        Order order;
        // I check whether the cart exists
        if (cartOrder.isPresent()) {
            order = cartOrder.get();

            // I check whether there is already an Order Product with this Product
            OrderProduct existingOrderProduct = order.getOrderProducts().stream()
                    .filter(op -> op.getProduct().getId().equals(orderProduct.getProduct().getId()))
                    .findFirst().orElse(null);

            if (existingOrderProduct != null) {
                int newQuantity = existingOrderProduct.getQuantity() + orderProduct.getQuantity();

                // If so, I check whether we have enough Product in stock
                // If so, I save the entities, if not, I throw an error
                if (newQuantity > product.getQuantity()) {
                    throw new InsufficientQuantityException();
                } else {
                    existingOrderProduct.setQuantity(newQuantity);
                    orderProductRepository.save(existingOrderProduct);
                }
            } else {
                // If there is no Order Product with such a Product in the order, I check the quantity in stock
                // If so, I save the entity, if not, I throw an error
                if (orderProduct.getQuantity() > product.getQuantity()) {
                    throw new InsufficientQuantityException();
                } else {
                    orderProduct.setProduct(product);
                    OrderProduct newOrderProduct = orderProductRepository.save(orderProduct);
                    order.getOrderProducts().add(newOrderProduct);
                }
            }

            order = orderService.saveOrder(order);
        } else {
            // If the shopping cart does not exist, I check whether we have enough products in stock
            // If we have this much in stock, save the entities, if not, I throw an error
            if (orderProduct.getQuantity() > product.getQuantity()) {
                throw new InsufficientQuantityException();
            } else {
                order = new Order();
                order.setOrderStatus(OrderStatus.CART);
                order = orderService.saveOrder(order);
                orderProduct.setProduct(product);
                OrderProduct newOrderProduct = orderProductRepository.save(orderProduct);
                order.setOrderProducts(new ArrayList<>(List.of(newOrderProduct)));
                order = orderService.saveOrder(order);
            }
        }

        return order;
    }

    @Override
    public void removeProductFromCart(Long id) {
        OrderProduct orderProduct = getOrderProduct(id);
        orderProductRepository.delete(orderProduct);
    }
}
