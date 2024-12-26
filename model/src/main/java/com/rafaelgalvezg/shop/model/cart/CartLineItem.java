package com.rafaelgalvezg.shop.model.cart;

import com.rafaelgalvezg.shop.model.money.Money;
import com.rafaelgalvezg.shop.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
@AllArgsConstructor
public class CartLineItem {

    private final Product product;
    private int quantity;

    public void increaseQuantityBy(int augend, int itemsInsStock)
        throws NotEnoughItemsInStockException{
        if(augend < 1){
            throw new IllegalArgumentException("You must add at least one item");
        }

        int newQuantity = quantity + augend;
        if(itemsInsStock < newQuantity){
            throw new NotEnoughItemsInStockException(
                    "Product %s has less item ins Stock (%d) than the requested total quantity (%d)"
                            .formatted(product.id(), product.itemInStock(), newQuantity),
                    product.itemInStock()
            );
        }

        this.quantity = newQuantity;
    }

    public Money subtotal(){
        return product.price().multiply(quantity);
    }
}
