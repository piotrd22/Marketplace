package com.example.marketplace.controllers;

import com.example.marketplace.dtos.request.cart.AddProductToCartDto;
import com.example.marketplace.dtos.response.CartDto;
import com.example.marketplace.mappers.CartMapper;
import com.example.marketplace.mappers.CartProductMapper;
import com.example.marketplace.models.Cart;
import com.example.marketplace.models.CartProduct;
import com.example.marketplace.services.cart.CartService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


// There should also be API versioning, but I don't do it in this project
@RestController
@RequestMapping("/api/cart")
public class CartController extends AbstractControllerBase {
    private final CartService cartService;
    private final CartProductMapper cartProductMapper;
    private final CartMapper cartMapper;

    public CartController(CartService cartService, CartProductMapper cartProductMapper, CartMapper cartMapper) {
        this.cartService = cartService;
        this.cartProductMapper = cartProductMapper;
        this.cartMapper = cartMapper;
    }

    @PostMapping("/cart-product")
    public ResponseEntity<CartDto> addProductToCart(@RequestBody @Valid AddProductToCartDto dto) {
        logger.info("Inside: CartController -> addProductToCart()...");
        CartProduct cartProduct = cartProductMapper.addProductToCartDtoToCartProduct(dto);
        Long userId = getUserId();
        Cart cart = cartService.addProductToCart(cartProduct, userId);
        return ResponseEntity.ok().body(cartMapper.cartToCartDto(cart));
    }

    @DeleteMapping("/cart-product/{id}")
    public ResponseEntity<CartDto> removeProductFromCart(@PathVariable Long id) {
        logger.info("Inside: CartController -> addProductFromCart()...");
        Long userId = getUserId();
        Cart cart = cartService.removeProductFromCart(id, userId);
        return ResponseEntity.ok().body(cartMapper.cartToCartDto(cart));
    }
}
