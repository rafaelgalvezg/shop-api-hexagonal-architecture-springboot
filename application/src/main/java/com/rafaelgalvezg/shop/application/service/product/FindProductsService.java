package com.rafaelgalvezg.shop.application.service.product;

import com.rafaelgalvezg.shop.application.port.in.product.FindProductsUseCase;
import com.rafaelgalvezg.shop.application.port.out.persistence.ProductRepository;
import com.rafaelgalvezg.shop.model.product.Product;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class FindProductsService implements FindProductsUseCase {

    private final ProductRepository productRepository;

    @Override
    public List<Product> findByNameOrDescription(String query){
        Objects.requireNonNull(query, "'query' must not be null");
        if(query.length() < 2){
            throw new IllegalArgumentException("'query' must be at least two characters long");
        }
        return productRepository.findByNameOrDescription(query);
    }
}
