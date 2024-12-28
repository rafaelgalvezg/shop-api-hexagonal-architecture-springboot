package com.rafaelgalvezg.shop.adapter.out.persistence.jpa;

import com.rafaelgalvezg.shop.application.port.out.persistence.CartRepository;
import com.rafaelgalvezg.shop.model.cart.Cart;
import com.rafaelgalvezg.shop.model.customer.CustomerId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@ConditionalOnProperty(name = "persistence", havingValue = "mysql")
@Repository
@RequiredArgsConstructor
public class JpaCartRepository implements CartRepository {

    private final  IJpaCartRepository cartRepository;


    @Override
    @Transactional
    public void save(Cart cart){
       cartRepository.save(CartMapper.toJpaEntity(cart));
    }

    @Override
    public Optional<Cart> findByCustomerId (CustomerId customerId){
       return cartRepository.findByCustomerId(customerId.value()).flatMap(CartMapper::toModelEntityOptional);
    }

    @Override
    @Transactional
    public void deleteByCustomerId(CustomerId customerId){
        CartJpaEntity cartJpaEntity = cartRepository.findByCustomerId(customerId.value()).orElseThrow();
        cartRepository.delete(cartJpaEntity);
    }
}
