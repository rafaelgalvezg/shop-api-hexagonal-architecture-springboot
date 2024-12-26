package com.rafaelgalvezg.shop.application.port.in.cart;

import com.rafaelgalvezg.shop.model.cart.Cart;
import com.rafaelgalvezg.shop.model.cart.NotEnoughItemsInStockException;
import com.rafaelgalvezg.shop.model.customer.CustomerId;
import com.rafaelgalvezg.shop.model.product.ProductId;

public interface AddToCartUseCase {

    Cart addToCart(CustomerId customerId, ProductId productId, int quantity)
        throws ProductNotFoundException,NotEnoughItemsInStockException;
}
