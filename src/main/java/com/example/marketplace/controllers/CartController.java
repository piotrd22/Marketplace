package com.example.marketplace.controllers;

import com.example.marketplace.dtos.request.cart.AddAddressToCartDto;
import com.example.marketplace.dtos.request.cart.AddPaymentToCartDto;
import com.example.marketplace.dtos.request.cart.AddProductToCartDto;
import com.example.marketplace.dtos.request.cart.UpdateProductQuantityInCartDto;
import com.example.marketplace.dtos.response.CartDto;
import com.example.marketplace.mappers.AddressMapper;
import com.example.marketplace.mappers.CartMapper;
import com.example.marketplace.mappers.CartProductMapper;
import com.example.marketplace.mappers.PaymentMapper;
import com.example.marketplace.models.Address;
import com.example.marketplace.models.Cart;
import com.example.marketplace.models.CartProduct;
import com.example.marketplace.models.Payment;
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
    private final AddressMapper addressMapper;
    private final PaymentMapper paymentMapper;

    public CartController(CartService cartService, CartProductMapper cartProductMapper, CartMapper cartMapper, AddressMapper addressMapper, PaymentMapper paymentMapper) {
        this.cartService = cartService;
        this.cartProductMapper = cartProductMapper;
        this.cartMapper = cartMapper;
        this.addressMapper = addressMapper;
        this.paymentMapper = paymentMapper;
    }

    @GetMapping
    public ResponseEntity<CartDto> getCartByUserId() {
        logger.info("Inside: CartController -> getCartByUserId()...");
        Long userId = getUserId();
        Cart cart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok().body(cartMapper.cartToCartDto(cart));
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

    @PutMapping("/cart-product/{id}")
    public ResponseEntity<CartDto> updateProductQuantityInCart(@PathVariable Long id, @RequestBody @Valid UpdateProductQuantityInCartDto dto) {
        logger.info("Inside: CartController -> updateProductQuantityInCart()...");
        Long userId = getUserId();
        Cart cart = cartService.updateProductQuantityInCart(userId, id, dto.getNewQuantity());
        return ResponseEntity.ok().body(cartMapper.cartToCartDto(cart));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCart() {
        logger.info("Inside: CartController -> deleteCart()...");
        Long userId = getUserId();
        cartService.deleteCart(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/address")
    public ResponseEntity<CartDto> addAddressToCart(@RequestBody @Valid AddAddressToCartDto dto) {
        logger.info("Inside: CartController -> addAddressToCart()...");
        Long userId = getUserId();
        Address address = addressMapper.addAddressToCartDtoToAddress(dto);
        Cart cart = cartService.saveAddressToCart(userId, address);
        return ResponseEntity.ok().body(cartMapper.cartToCartDto(cart));
    }

    @PutMapping("/payment")
    public ResponseEntity<CartDto> addPaymentToCart(@RequestBody @Valid AddPaymentToCartDto dto) {
        logger.info("Inside: CartController -> addPaymentToCart()...");
        Long userId = getUserId();
        Payment payment = paymentMapper.addPaymentToCartDtoToPayment(dto);
        Cart cart = cartService.savePaymentToCart(userId, payment);
        return ResponseEntity.ok().body(cartMapper.cartToCartDto(cart));
    }
}
