package com.example.marketplace.controllers;

import com.example.marketplace.dtos.request.orderProduct.AddProductToCartDto;
import com.example.marketplace.dtos.response.OrderDto;
import com.example.marketplace.mappers.OrderMapper;
import com.example.marketplace.mappers.OrderProductMapper;
import com.example.marketplace.models.Order;
import com.example.marketplace.models.OrderProduct;
import com.example.marketplace.services.orderProduct.OrderProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order-product")
public class OrderProductController extends AbstractControllerBase {

    private final OrderProductService orderProductService;
    private final OrderProductMapper orderProductMapper;
    private final OrderMapper orderMapper;

    public OrderProductController(OrderProductService orderProductService, OrderProductMapper orderProductMapper, OrderMapper orderMapper) {
        this.orderProductService = orderProductService;
        this.orderProductMapper = orderProductMapper;
        this.orderMapper = orderMapper;
    }

    @PostMapping
    public ResponseEntity<OrderDto> addProductToCart(@RequestBody @Valid AddProductToCartDto dto) {
        logger.info("Inside: OrderProductController -> addProductToCart()...");
        OrderProduct orderProduct = orderProductMapper.addProductToCartDtoToOrderProduct(dto);
        Order order = orderProductService.addProductToCart(orderProduct);
        return ResponseEntity.ok().body(orderMapper.orderToOrderDto(order));
    }
}
