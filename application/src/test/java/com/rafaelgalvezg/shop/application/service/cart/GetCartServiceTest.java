package com.rafaelgalvezg.shop.application.service.cart;

import com.rafaelgalvezg.shop.application.port.out.persistence.CartRepository;
import com.rafaelgalvezg.shop.model.cart.Cart;
import com.rafaelgalvezg.shop.model.cart.NotEnoughItemsInStockException;
import com.rafaelgalvezg.shop.model.customer.CustomerId;
import com.rafaelgalvezg.shop.model.product.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static com.rafaelgalvezg.shop.model.money.TestMoneyFactory.euros;
import static com.rafaelgalvezg.shop.model.product.TestProductFactory.createTestProduct;
import static org.mockito.Mockito.mock;
import static org.assertj.core.api.Assertions.assertThat;

class GetCartServiceTest {

    private static final CustomerId TEST_CUSTOMER_ID = new CustomerId(61157);
    private static final Product TEST_PRODUCT_1 = createTestProduct(euros(19,99));
    private static final Product TEST_PRODUCT_2 = createTestProduct(euros(25, 99));

    private final CartRepository cartRepository = mock(CartRepository.class);
    private final GetCartService getCartService = new GetCartService(cartRepository)
;
    @Test
    @DisplayName("given cart is persisted abd get cart return persisted cart")
    void test1() throws NotEnoughItemsInStockException {
        Cart persistedCart = new Cart(TEST_CUSTOMER_ID);
        persistedCart.addProduct(TEST_PRODUCT_1, 1);
        persistedCart.addProduct(TEST_PRODUCT_2, 5);

        Mockito.when(cartRepository.findByCustomerId(TEST_CUSTOMER_ID))
                .thenReturn(Optional.of(persistedCart));

        Cart cart = getCartService.getCart(TEST_CUSTOMER_ID);

        assertThat(cart).isSameAs(persistedCart);
    }

    @Test
    @DisplayName("given cart is not persisted")
    void test2 (){
        Mockito.when(cartRepository.findByCustomerId(TEST_CUSTOMER_ID)).thenReturn(Optional.empty());

        Cart cart = getCartService.getCart(TEST_CUSTOMER_ID);

        assertThat(cart).isNotNull();
        assertThat(cart.lineItems()).isEmpty();
    }
}
