package com.example.marketplace.services.cart;

import com.example.marketplace.exceptions.AlreadyExistsException;
import com.example.marketplace.exceptions.InsufficientQuantityException;
import com.example.marketplace.exceptions.NotFoundException;
import com.example.marketplace.models.*;
import com.example.marketplace.repositories.AddressRepository;
import com.example.marketplace.repositories.CartProductRepository;
import com.example.marketplace.repositories.CartRepository;
import com.example.marketplace.repositories.PaymentRepository;
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
    private final PaymentRepository paymentRepository;
    private final AddressRepository addressRepository;
    private final ProductService productService;

    public CartServiceImpl(CartProductRepository cartProductRepository, CartRepository cartRepository, PaymentRepository paymentRepository, AddressRepository addressRepository, ProductService productService) {
        this.cartProductRepository = cartProductRepository;
        this.cartRepository = cartRepository;
        this.paymentRepository = paymentRepository;
        this.addressRepository = addressRepository;
        this.productService = productService;
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException("Cart with user id '%s' not found".formatted(userId)));
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
        Cart cart = getCartByUserId(userId);
        CartProduct cartProduct = getCartProduct(id);
        cart.getCartProducts().remove(cartProduct);
        cartProductRepository.delete(cartProduct);
        cart = cartRepository.save(cart);
        return cart;
    }

    @Override
    public Cart updateProductQuantityInCart(Long userId, Long cartProductId, int newQuantity) {
        Cart cart = getCartByUserId(userId);
        CartProduct cartProduct = getCartProduct(cartProductId);
        Product product = productService.getProduct(cartProduct.getProduct().getId());

        if (!cart.getCartProducts().contains(cartProduct)) {
            throw new NotFoundException("The cart product with id '%s' was not found in the cart with id '%s'".formatted(cartProductId, cart.getId()));
        }

        if (newQuantity > product.getQuantity()) {
            throw new InsufficientQuantityException();
        }

        cart.getCartProducts().remove(cartProduct);
        cartProduct.setQuantity(newQuantity);
        cart.getCartProducts().add(cartProduct);
        cartProductRepository.save(cartProduct);

        return cart;
    }

    @Override
    @Transactional
    public Cart addAddressToCart(Long userId, Address address) {
        Cart cart = getCartByUserId(userId);

        if (cart.getAddress() != null) {
            throw new AlreadyExistsException("Cart with user id '%s' already have address.".formatted(userId));
        }

        address.setUserId(userId);
        address = addressRepository.save(address);
        cart.setAddress(address);
        return cartRepository.save(cart);
    }

    @Override
    @Transactional
    public Cart addPaymentToCart(Long userId, Payment payment) {
        Cart cart = getCartByUserId(userId);

        if (cart.getPayment() != null) {
            throw new AlreadyExistsException("Cart with user id '%s' already have payment method.".formatted(userId));
        }

        payment.setUserId(userId);
        payment = paymentRepository.save(payment);
        cart.setPayment(payment);
        return cartRepository.save(cart);
    }

    @Override
    public Cart updateAddressInCart(Long userId, Address address) {
        return null;
    }

    @Override
    public Cart updatePaymentInCart(Long userId, Payment payment) {
        return null;
    }

    @Override
    @Transactional
    public void deleteCart(Long userId) {
        Cart cart = getCartByUserId(userId);

        for (CartProduct cartProduct : cart.getCartProducts()) {
            cartProductRepository.delete(cartProduct);
        }

        if (cart.getAddress() != null) {
            addressRepository.delete(cart.getAddress());
        }

        if (cart.getPayment() != null) {
            paymentRepository.delete(cart.getPayment());
        }

        cartRepository.delete(cart);
    }

    private CartProduct getCartProduct(Long id) {
        return cartProductRepository.findById(id).orElseThrow(() -> new NotFoundException(id, "CartProduct"));
    }
}
