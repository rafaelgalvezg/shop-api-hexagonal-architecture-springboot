package com.rafaelgalvezg.shop.adapter.in.rest.cart;

import com.rafaelgalvezg.shop.application.port.in.cart.EmptyCartUseCase;
import com.rafaelgalvezg.shop.model.customer.CustomerId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.rafaelgalvezg.shop.adapter.in.rest.common.CustomerIdParser.parseCustomerId;

@RequiredArgsConstructor
@RestController
@RequestMapping("/carts")
public class EmptyCartController {
    private final EmptyCartUseCase emptyCartUseCase;

    @PreAuthorize("hasRole('ROLE_spring-keycloak-client_empty-cart')")
    @DeleteMapping("{customerId}")
    public ResponseEntity<Void> deleteCart(@PathVariable("customerId") String customerIdString){
        CustomerId customerId = parseCustomerId(customerIdString);

        try {
            emptyCartUseCase.emptyCart(customerId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }


    }
}
