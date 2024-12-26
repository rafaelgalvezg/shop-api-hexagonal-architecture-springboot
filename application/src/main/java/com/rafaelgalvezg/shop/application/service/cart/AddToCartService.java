package com.rafaelgalvezg.shop.application.service.cart;

import com.rafaelgalvezg.shop.application.port.in.cart.AddToCartUseCase;
import com.rafaelgalvezg.shop.application.port.in.cart.ProductNotFoundException;
import com.rafaelgalvezg.shop.application.port.out.persistence.CartRepository;
import com.rafaelgalvezg.shop.application.port.out.persistence.ProductRepository;
import com.rafaelgalvezg.shop.model.cart.Cart;
import com.rafaelgalvezg.shop.model.cart.NotEnoughItemsInStockException;
import com.rafaelgalvezg.shop.model.customer.CustomerId;
import com.rafaelgalvezg.shop.model.product.Product;
import com.rafaelgalvezg.shop.model.product.ProductId;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class AddToCartService implements AddToCartUseCase {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Override
    public Cart addToCart(CustomerId customerId, ProductId productId, int quantity)
        throws ProductNotFoundException, NotEnoughItemsInStockException {
        Objects.requireNonNull(customerId, "'customerId' must not be null");
        Objects.requireNonNull(productId, "'productId' must not be null");

        if(quantity < 1){
            throw  new IllegalArgumentException("'quantity' mus be greater than 0");
        }

        Product product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);

        Cart cart = cartRepository
                .findByCustomerId(customerId)
                .orElseGet(()-> new Cart(customerId));

        cart.addProduct(product, quantity);

        cartRepository.save(cart);

        return cart;
    }
}
