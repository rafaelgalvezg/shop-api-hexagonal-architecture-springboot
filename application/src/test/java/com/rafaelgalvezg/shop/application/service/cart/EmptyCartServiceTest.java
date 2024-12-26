package com.rafaelgalvezg.shop.application.service.cart;

import com.rafaelgalvezg.shop.application.port.out.persistence.CartRepository;
import com.rafaelgalvezg.shop.model.customer.CustomerId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class EmptyCartServiceTest {
    private static final CustomerId TEST_CUSTOMER_ID = new CustomerId(61157);

    private final CartRepository cartRepository = mock(CartRepository.class);
    private final EmptyCartService emptyCartService = new EmptyCartService(cartRepository);

    @Test
    @DisplayName("empty cart invoke delete on the persistence port")
    void test1(){
      emptyCartService.emptyCart(TEST_CUSTOMER_ID);

      verify(cartRepository).deleteByCustomerId(TEST_CUSTOMER_ID);
    }

}
