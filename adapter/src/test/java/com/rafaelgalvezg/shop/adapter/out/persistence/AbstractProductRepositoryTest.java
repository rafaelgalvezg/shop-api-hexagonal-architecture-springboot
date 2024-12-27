package com.rafaelgalvezg.shop.adapter.out.persistence;

import com.rafaelgalvezg.shop.application.port.out.persistence.ProductRepository;
import com.rafaelgalvezg.shop.model.product.Product;
import com.rafaelgalvezg.shop.model.product.ProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractProductRepositoryTest<T extends ProductRepository> {

    private T productRepository;

    @BeforeEach
    void initRepository() {
        productRepository = createProductRepository();
    }

    protected abstract T createProductRepository();

    @Test
    void givenTestProductsAndATestProductId_findById_returnsATestProduct() {
        ProductId productId = DemoProducts.COMPUTER_MONITOR.id();

        Optional<Product> product = productRepository.findById(productId);

        assertThat(product).contains(DemoProducts.COMPUTER_MONITOR);
    }

    @Test
    void givenTheIdOfAProductNotPersisted_findById_returnsAnEmptyOptional() {
        ProductId productId = new ProductId("00000");

        Optional<Product> product = productRepository.findById(productId);

        assertThat(product).isEmpty();
    }

    @Test
    void
    givenTestProductsAndASearchQueryNotMatchingAndProduct_findByNameOrDescription_returnsAnEmptyList() {
        String query = "not matching any product";

        List<Product> products = productRepository.findByNameOrDescription(query);

        assertThat(products).isEmpty();
    }

    @Test
    void
    givenTestProductsAndASearchQueryMatchingOneProduct_findByNameOrDescription_returnsThatProduct() {
        String query = "lights";

        List<Product> products = productRepository.findByNameOrDescription(query);

        assertThat(products).containsExactlyInAnyOrder(DemoProducts.LED_LIGHTS);
    }

    @Test
    void
    givenTestProductsAndASearchQueryMatchingTwoProducts_findByNameOrDescription_returnsThoseProducts() {
        String query = "monitor";

        List<Product> products = productRepository.findByNameOrDescription(query);

        assertThat(products)
                .containsExactlyInAnyOrder(DemoProducts.COMPUTER_MONITOR, DemoProducts.MONITOR_DESK_MOUNT);
    }
}