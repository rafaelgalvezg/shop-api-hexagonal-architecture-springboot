package com.rafaelgalvezg.shop;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringAppConfig {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartRepository cartRepository;

    @Bean
    GetCartUseCase getCartUseCase() {
        return new GetCartService(cartRepository);
    }

    @Bean
    EmptyCartUseCase emptyCartUseCase() {
        return new EmptyCartService(cartRepository);
    }

    @Bean
    AddToCartUseCase addToCartUseCase() {
        return new AddToCartService(cartRepository, productRepository);
    }

    @Bean
    FindProductsUseCase findProductsUseCase() {
        return new FindProductsService(productRepository);
    }

}
