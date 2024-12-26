package com.rafaelgalvezg.shop.application.port.out.persistence;

import com.rafaelgalvezg.shop.model.cart.Cart;
import com.rafaelgalvezg.shop.model.customer.CustomerId;

import java.util.Optional;

public interface CartRepository {

    void save(Cart cart);

    Optional<Cart> findByCustomerId(CustomerId customerId);

    void deleteByCustomerId(CustomerId customerId);

}
