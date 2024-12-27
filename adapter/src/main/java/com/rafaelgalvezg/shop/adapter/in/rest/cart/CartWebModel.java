package com.rafaelgalvezg.shop.adapter.in.rest.cart;

import com.rafaelgalvezg.shop.model.cart.Cart;
import com.rafaelgalvezg.shop.model.money.Money;

import java.util.List;

public record CartWebModel(
        List<CartLineItemWebModel> lineItems, int numberOfItems, Money subtotal) {

    static CartWebModel fromDomainModel(Cart cart){
        return new CartWebModel(
                cart.lineItems().stream().map(CartLineItemWebModel::fromDomainModel).toList(),
                cart.numberOfItems(),
                cart.subTotal()
        );
    }
}
