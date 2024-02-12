package com.example.marketplace.controllers;

import com.example.marketplace.dtos.response.OrderDto;
import com.example.marketplace.mappers.OrderMapper;
import com.example.marketplace.models.Order;
import com.example.marketplace.services.order.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController extends AbstractControllerBase {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    public OrderController(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getOrders(Pageable pageable) {
        logger.info("Inside: OrderController -> getOrders()...");
        List<Order> orders = orderService.getOrders(pageable);
        return ResponseEntity.ok().body(orders.stream().map(orderMapper::orderToOrderDto).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable Long id) {
        logger.info("Inside: OrderController -> getOrder()...");
        Order order = orderService.getOrder(id);
        return ResponseEntity.ok().body(orderMapper.orderToOrderDto(order));
    }

    @GetMapping("/user")
    public ResponseEntity<Page<OrderDto>> getOrdersByUserId(Pageable pageable) {
        logger.info("Inside: OrderController -> getOrdersByUserId()...");
        Long userId = getUserId();
        Page<Order> orders = orderService.getOrdersByUserId(userId, pageable);
        Page<OrderDto> orderDtos = orders.map(orderMapper::orderToOrderDto);
        return ResponseEntity.ok().body(orderDtos);
    }

    @PostMapping
    public ResponseEntity<OrderDto> placeOrder(HttpServletRequest request) {
        logger.info("Inside: OrderController -> placeOrder()...");
        Long userId = getUserId();
        Order order = orderService.placeOrder(userId);
        URI location = getURILocationFromRequest(order.getId(), request);
        return ResponseEntity.created(location).body(orderMapper.orderToOrderDto(order));
    }
}
