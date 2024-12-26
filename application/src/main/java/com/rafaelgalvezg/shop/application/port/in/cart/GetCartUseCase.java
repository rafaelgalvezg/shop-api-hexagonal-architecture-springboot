package com.rafaelgalvezg.shop.application.port.in.cart;

import com.rafaelgalvezg.shop.model.cart.Cart;
import com.rafaelgalvezg.shop.model.customer.CustomerId;

public interface GetCartUseCase {

    Cart getCart(CustomerId customerId);
}
