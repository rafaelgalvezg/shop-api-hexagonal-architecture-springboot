package com.rafaelgalvezg.shop.application.service.cart;

import com.rafaelgalvezg.shop.application.port.in.cart.GetCartUseCase;
import com.rafaelgalvezg.shop.application.port.out.persistence.CartRepository;
import com.rafaelgalvezg.shop.model.cart.Cart;
import com.rafaelgalvezg.shop.model.customer.CustomerId;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class GetCartService implements GetCartUseCase {

    private final CartRepository cartRepository;

    @Override
    public Cart getCart(CustomerId customerId){
        Objects.requireNonNull(customerId, "'customerId' mus not be null");

        return cartRepository
                .findByCustomerId(customerId)
                .orElseGet(() -> new Cart(customerId));
    }
}
