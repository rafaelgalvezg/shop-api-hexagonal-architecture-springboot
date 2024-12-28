package com.rafaelgalvezg.shop.adapter.out.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IJpaProductRepository extends JpaRepository<ProductJpaEntity, String> {

    @Query("SELECT p FROM ProductJpaEntity p WHERE p.name LIKE %:query% OR p.description LIKE %:query%")
    List<ProductJpaEntity> findByNameOrDescription(String query);
}
