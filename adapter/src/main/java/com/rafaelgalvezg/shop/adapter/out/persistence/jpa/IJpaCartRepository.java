package com.rafaelgalvezg.shop.adapter.out.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IJpaCartRepository extends JpaRepository<CartJpaEntity, Integer> {
    Optional<CartJpaEntity> findByCustomerId(int customerId);
}
