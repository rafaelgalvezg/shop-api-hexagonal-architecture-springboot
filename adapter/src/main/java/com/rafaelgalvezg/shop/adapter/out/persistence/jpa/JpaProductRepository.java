package com.rafaelgalvezg.shop.adapter.out.persistence.jpa;

import com.rafaelgalvezg.shop.adapter.out.persistence.DemoProducts;
import com.rafaelgalvezg.shop.application.port.out.persistence.ProductRepository;
import com.rafaelgalvezg.shop.model.product.Product;
import com.rafaelgalvezg.shop.model.product.ProductId;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@ConditionalOnProperty(name = "persistence", havingValue = "mysql")
@Repository
@RequiredArgsConstructor
public class JpaProductRepository implements ProductRepository {

    private final IJpaProductRepository productRepository;

    @PostConstruct
    private void createDemoProducts() {
        DemoProducts.DEMO_PRODUCTS.forEach(this::save);
    }

    @Override
    @Transactional
    public void save(Product product) {
        productRepository.save(ProductMapper.toJpaEntity(product));
    }

    @Override
    public Optional<Product> findById(ProductId productId) {
        return productRepository.findById(productId.value()).flatMap(ProductMapper::toModelEntityOptional);
    }

    @Override
    public List<Product> findByNameOrDescription(String queryString) {
        List<ProductJpaEntity> products = productRepository.findByNameOrDescription(queryString);
        return ProductMapper.toModelEntities(products);
    }

}
