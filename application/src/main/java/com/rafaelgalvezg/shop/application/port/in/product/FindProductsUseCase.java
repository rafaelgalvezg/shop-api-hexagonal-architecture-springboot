package com.rafaelgalvezg.shop.application.port.in.product;

import com.rafaelgalvezg.shop.model.product.Product;

import java.util.List;

public interface FindProductsUseCase {

    List<Product> findByNameOrDescription(String query);

}
