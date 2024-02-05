package com.example.marketplace.services.cart;

import com.example.marketplace.exceptions.InsufficientQuantityException;
import com.example.marketplace.exceptions.NotFoundException;
import com.example.marketplace.models.*;
import com.example.marketplace.repositories.CartProductRepository;
import com.example.marketplace.repositories.CartRepository;
import com.example.marketplace.services.product.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private final CartProductRepository cartProductRepository;
    private final CartRepository cartRepository;
    private final ProductService productService;

    public CartServiceImpl(CartProductRepository cartProductRepository, CartRepository cartRepository, ProductService productService) {
        this.cartProductRepository = cartProductRepository;
        this.cartRepository = cartRepository;
        this.productService = productService;
    }

    @Override
    @Transactional
    public Cart addProductToCart(CartProduct cartProduct, Long userId) {
        Product product = productService.getProduct(cartProduct.getProduct().getId());
        Optional<Cart> oldCart = cartRepository.findByUserId(userId);

        Cart cart;
        // I check whether the cart exists
        if (oldCart.isPresent()) {
            cart = oldCart.get();

            // I check whether there is already an Order Product with this Product
            CartProduct existingCartProduct = cart.getCartProducts().stream()
                    .filter(op -> op.getProduct().getId().equals(cartProduct.getProduct().getId()))
                    .findFirst().orElse(null);

            if (existingCartProduct != null) {
                int newQuantity = existingCartProduct.getQuantity() + cartProduct.getQuantity();

                // If so, I check whether we have enough Product in stock
                // If so, I save the entities, if not, I throw an error
                if (newQuantity > product.getQuantity()) {
                    throw new InsufficientQuantityException();
                } else {
                    existingCartProduct.setQuantity(newQuantity);
                    cartProductRepository.save(existingCartProduct);
                }
            } else {
                // If there is no Cart Product with such a Product in the order, I check the quantity in stock
                // If so, I save the entity, if not, I throw an error
                if (cartProduct.getQuantity() > product.getQuantity()) {
                    throw new InsufficientQuantityException();
                } else {
                    cartProduct.setProduct(product);
                    CartProduct newCartProduct = cartProductRepository.save(cartProduct);
                    cart.getCartProducts().add(newCartProduct);
                }
            }

            cart = cartRepository.save(cart);
        } else {
            // If the shopping cart does not exist, I check whether we have enough products in stock
            // If we have this much in stock, save the entities, if not, I throw an error
            if (cartProduct.getQuantity() > product.getQuantity()) {
                throw new InsufficientQuantityException();
            } else {
                cart = new Cart();
                cart.setUserId(userId);
                cart = cartRepository.save(cart);
                cartProduct.setProduct(product);
                CartProduct newCartProduct = cartProductRepository.save(cartProduct);
                cart.setCartProducts(new ArrayList<>(List.of(newCartProduct)));
                cart = cartRepository.save(cart);
            }
        }

        return cart;
    }

    @Override
    @Transactional
    // TODO Think about whether when you remove the last product from the cart, you should also remove it
    public Cart removeProductFromCart(Long id, Long userId) {
        Optional<Cart> oldCart = cartRepository.findByUserId(userId);

        if (oldCart.isEmpty()) {
            throw new NotFoundException("Cart not found");
        }

        CartProduct cartProduct = getCartProduct(id);

        Cart cart = oldCart.get();
        cart.getCartProducts().remove(cartProduct);
        cartProductRepository.delete(cartProduct);

        cart = cartRepository.save(cart);
        return cart;
    }

    private CartProduct getCartProduct(Long id) {
        return cartProductRepository.findById(id).orElseThrow(() -> new NotFoundException(id, "CartProduct"));
    }
}
