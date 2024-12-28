package com.rafaelgalvezg.shop.adapter.out.persistence.inmemory;

import com.rafaelgalvezg.shop.adapter.out.persistence.DemoProducts;
import com.rafaelgalvezg.shop.application.port.out.persistence.ProductRepository;
import com.rafaelgalvezg.shop.model.product.Product;
import com.rafaelgalvezg.shop.model.product.ProductId;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@ConditionalOnProperty(name = "persistence", havingValue = "inmemory", matchIfMissing = true)
@Repository
public class InMemoryProductRepository implements ProductRepository {

    private final Map<ProductId, Product> products = new ConcurrentHashMap<>();

    public InMemoryProductRepository() {
        createDemoProducts();
    }

    private void createDemoProducts(){
        DemoProducts.DEMO_PRODUCTS.forEach(this::save);
    }

    @Override
    public void save(Product product){
        products.put(product.id(), product);
    }

    @Override
    public Optional<Product> findById(ProductId productId){
        return Optional.ofNullable(products.get(productId));
    }

    @Override
    public List<Product> findByNameOrDescription(String query){
        String queryLowerCase = query.toLowerCase(Locale.ROOT);

        return products.values().stream()
                .filter(product -> matchesQuery(product, queryLowerCase))
                .toList();
    }

    private boolean matchesQuery(Product product, String query){
        return product.name().toLowerCase(Locale.ROOT).contains(query)
                || product.description().toLowerCase(Locale.ROOT).contains(query);
    }

}
