package com.rafaelgalvezg.shop.bootstrap;

import com.rafaelgalvezg.shop.adapter.in.rest.cart.AddToCartController;
import com.rafaelgalvezg.shop.adapter.in.rest.cart.EmptyCartController;
import com.rafaelgalvezg.shop.adapter.in.rest.cart.GetCartController;
import com.rafaelgalvezg.shop.adapter.in.rest.product.FindProductsController;
import com.rafaelgalvezg.shop.adapter.out.persistence.inmemory.InMemoryCartRepository;
import com.rafaelgalvezg.shop.adapter.out.persistence.inmemory.InMemoryProductRepository;
import com.rafaelgalvezg.shop.adapter.out.persistence.jpa.EntityManagerFactoryFactory;
import com.rafaelgalvezg.shop.adapter.out.persistence.jpa.JpaCartRepository;
import com.rafaelgalvezg.shop.adapter.out.persistence.jpa.JpaProductRepository;
import com.rafaelgalvezg.shop.application.port.in.cart.AddToCartUseCase;
import com.rafaelgalvezg.shop.application.port.in.cart.EmptyCartUseCase;
import com.rafaelgalvezg.shop.application.port.in.cart.GetCartUseCase;
import com.rafaelgalvezg.shop.application.port.in.product.FindProductsUseCase;
import com.rafaelgalvezg.shop.application.port.out.persistence.CartRepository;
import com.rafaelgalvezg.shop.application.port.out.persistence.ProductRepository;
import com.rafaelgalvezg.shop.application.service.cart.AddToCartService;
import com.rafaelgalvezg.shop.application.service.cart.EmptyCartService;
import com.rafaelgalvezg.shop.application.service.cart.GetCartService;
import com.rafaelgalvezg.shop.application.service.product.FindProductsService;
import jakarta.persistence.EntityManagerFactory;
import jakarta.ws.rs.core.Application;

import java.util.Set;


public class RestEasyUndertowShopApplication extends Application {

    private CartRepository cartRepository;
    private ProductRepository productRepository;

    @Override
    public Set<Object> getSingletons(){
        initPersistenceAdapters();
        return Set.of(
                addToCartController(),
                getCartController(),
                emptyCartController(),
                findProductsController()
        );
    }

    private void initPersistenceAdapters(){
        String persistence = System.getProperty("persistence", "inmemory");
        switch (persistence){
            case "inmemory" -> initInMemoryAdapters();
            case "mysql" -> initMysqlAdapters();
            default -> throw new IllegalArgumentException(
                    "Invalid 'persistence' property"
            );
        }
    }

    private void initInMemoryAdapters(){
        cartRepository = new InMemoryCartRepository();
        productRepository = new InMemoryProductRepository();
    }

    private void initMysqlAdapters(){
        EntityManagerFactory entityManagerFactory =
                EntityManagerFactoryFactory.createMysqlEntityManagerFactory(
                        "jdbc:mysql://localhost:3306/shop", "root", "test"
                );

        cartRepository = new JpaCartRepository(entityManagerFactory);
        productRepository = new JpaProductRepository(entityManagerFactory);

    }

    private GetCartController getCartController(){
        GetCartUseCase getCartUseCase = new GetCartService(cartRepository);
        return new GetCartController(getCartUseCase);
    }

    private AddToCartController addToCartController(){
        AddToCartUseCase addToCartUseCase = new AddToCartService(cartRepository, productRepository);
        return new AddToCartController(addToCartUseCase);
    }

    private EmptyCartController emptyCartController(){
        EmptyCartUseCase emptyCartUseCase = new EmptyCartService(cartRepository);
        return new EmptyCartController(emptyCartUseCase);
    }

    private FindProductsController findProductsController(){
        FindProductsUseCase findProductsUseCase = new FindProductsService(productRepository);
        return new FindProductsController(findProductsUseCase);
    }

}
