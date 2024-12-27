package com.rafaelgalvezg.shop.adapter.out.persistence.jpa;

import com.rafaelgalvezg.shop.model.cart.Cart;
import com.rafaelgalvezg.shop.model.cart.CartLineItem;
import com.rafaelgalvezg.shop.model.customer.CustomerId;

import java.util.Optional;

public class CartMapper {

    private  CartMapper(){}

    static  CartJpaEntity toJpaEntity(Cart cart){
        CartJpaEntity cartJpaEntity = new CartJpaEntity();
        cartJpaEntity.setCustomerId(cart.id().value());

        cartJpaEntity.setLineItems(
                cart.lineItems().stream().map(lineItem -> toJpaEntity(cartJpaEntity, lineItem)).toList()
        );

        return cartJpaEntity;

    }

    static  CartLineItemJpaEntity toJpaEntity(CartJpaEntity cartJpaEntity, CartLineItem cartLineItem){
        ProductJpaEntity productJpaEntity = new ProductJpaEntity();
        productJpaEntity.setId(cartLineItem.product().id().value());

        CartLineItemJpaEntity entity = new CartLineItemJpaEntity();
        entity.setCart(cartJpaEntity);
        entity.setProduct(productJpaEntity);
        entity.setQuantity(cartLineItem.quantity());

        return entity;
    }

    static Optional<Cart> toModelEntityOptional (CartJpaEntity cartJpaEntity){
        if(cartJpaEntity == null){
            return  Optional.empty();
        }

        CustomerId customerId = new CustomerId(cartJpaEntity.getCustomerId());
        Cart cart = new Cart(customerId);

        for(CartLineItemJpaEntity lineItemJpaEntity : cartJpaEntity.getLineItems()){
            cart.putProductIgnoringNotEnoughItemsInStock(
                   ProductMapper.toModelEntity(lineItemJpaEntity.getProduct()),
                   lineItemJpaEntity.getQuantity()
            );
        }

        return  Optional.of(cart);
    }

}
