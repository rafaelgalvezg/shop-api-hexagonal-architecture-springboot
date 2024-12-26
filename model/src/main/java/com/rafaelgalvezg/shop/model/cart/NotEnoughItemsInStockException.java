package com.rafaelgalvezg.shop.model.cart;

public class NotEnoughItemsInStockException extends Exception {
    private final int itemsIntStock;

    public NotEnoughItemsInStockException(String message, int itemsInStock) {
        super(message);
        this.itemsIntStock = itemsInStock;
    }

    public int itemInStock(){
        return itemsIntStock;
    }
}
