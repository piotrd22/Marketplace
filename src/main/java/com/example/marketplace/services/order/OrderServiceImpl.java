package com.example.marketplace.services.order;

import com.example.marketplace.enums.OrderStatus;
import com.example.marketplace.exceptions.BadRequestException;
import com.example.marketplace.exceptions.InsufficientQuantityException;
import com.example.marketplace.exceptions.NotFoundException;
import com.example.marketplace.models.*;
import com.example.marketplace.repositories.*;
import com.example.marketplace.services.cart.CartService;
import com.example.marketplace.services.product.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final PaymentRepository paymentRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;

    public OrderServiceImpl(OrderRepository orderRepository, OrderProductRepository orderProductRepository, PaymentRepository paymentRepository, ProductRepository productRepository, CartService cartService) {
        this.orderRepository = orderRepository;
        this.orderProductRepository = orderProductRepository;
        this.paymentRepository = paymentRepository;
        this.productRepository = productRepository;
        this.cartService = cartService;
    }

    @Override
    @Transactional
    public Order placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);

        // There should be better validation here, but it's enough for a project like this
        if (cart.getPayment() == null) {
            throw new BadRequestException("Cart is not ready for placing order. Payment is not set.");
        }
        if (cart.getAddress() == null) {
            throw new BadRequestException("Cart is not ready for placing order. Address is not set.");
        }
        if (cart.getCartProducts().isEmpty()) {
            throw new BadRequestException("Cart is not ready for placing order. Cart is empty.");
        }

        List<OrderProduct> orderProducts = new ArrayList<>();
        List<Product> updatedProducts = new ArrayList<>();

        for (CartProduct cartProduct : cart.getCartProducts()) {

            if (cartProduct.getProduct().getQuantity() < cartProduct.getQuantity()) {
                throw new InsufficientQuantityException();
            }

            Product product = cartProduct.getProduct();
            int newQuantity = product.getQuantity() - cartProduct.getQuantity();
            product.setQuantity(newQuantity);

            OrderProduct orderProduct = OrderProduct.builder()
                    .product(product)
                    .quantity(cartProduct.getQuantity())
                    .productPrice(cartProduct.getProductPrice())
                    .build();

            updatedProducts.add(product);
            orderProducts.add(orderProduct);
        }

        boolean isPaid = payForOrder();
        LocalDateTime payTime = LocalDateTime.now();
        if (!isPaid) {
            throw new BadRequestException("Payment failed. Try again.");
        }

        productRepository.saveAll(updatedProducts);
        orderProducts = orderProductRepository.saveAll(orderProducts);

        Order newOrder = Order.builder()
                .orderPrice(cart.getCartPrice())
                .orderProducts(orderProducts)
                .orderStatus(OrderStatus.PAID)
                .payment(cart.getPayment())
                .userId(cart.getUserId())
                .address(cart.getAddress())
                .build();

        Payment payment = newOrder.getPayment();
        payment.setPaymentDate(payTime);
        payment = paymentRepository.save(payment);
        newOrder.setPayment(payment);

        newOrder = orderRepository.save(newOrder);
        cartService.deleteCart(userId);
        return newOrder;
    }

    @Override
    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new NotFoundException(id, "Order"));
    }

    @Override
    public List<Order> getOrders(Pageable pageable) {
        return orderRepository.findAll(pageable).getContent();
    }

    @Override
    public List<Order> getOrdersByUserId(Long userId, Pageable pageable) {
        return orderRepository.findByUserId(userId, pageable).getContent();
    }

    // Mock -- 9 out of 10 cases will be paid
    private boolean payForOrder() {
        Random random = new Random();
        int randomNumber = random.nextInt(10);
        return randomNumber < 9;
    }
}
