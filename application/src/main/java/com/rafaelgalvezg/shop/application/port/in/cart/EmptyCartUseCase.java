package com.rafaelgalvezg.shop.application.port.in.cart;

import com.rafaelgalvezg.shop.model.customer.CustomerId;

public interface EmptyCartUseCase {

    void emptyCart(CustomerId customerId);
}
