package com.rafaelgalvezg.shop.model.cart;

import com.rafaelgalvezg.shop.model.customer.CustomerId;
import com.rafaelgalvezg.shop.model.money.Money;
import com.rafaelgalvezg.shop.model.product.Product;
import com.rafaelgalvezg.shop.model.product.ProductId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Accessors(fluent = true)
@RequiredArgsConstructor
public class Cart {

    @Getter final CustomerId id;
    private final Map<ProductId, CartLineItem> lineItems = new LinkedHashMap<>();

    public void addProduct(Product product, int quantity) throws NotEnoughItemsInStockException{
        lineItems
                .computeIfAbsent(product.id(), ignored -> new CartLineItem(product))
                .increaseQuantityBy(quantity, product.itemInStock());
    }

    public void putProductIgnoringNotEnoughItemsInStock(Product product, int quantity){
        lineItems.put(product.id(), new CartLineItem(product, quantity));
    }

    public List<CartLineItem> lineItems(){
        return List.copyOf(lineItems.values());
    }

    public int numberOfItems(){
        return lineItems.values().stream().mapToInt(CartLineItem::quantity).sum();
    }

    public Money subTotal(){
        return lineItems.values().stream().map(CartLineItem::subtotal).reduce(Money::add).orElse(null);
    }
}
