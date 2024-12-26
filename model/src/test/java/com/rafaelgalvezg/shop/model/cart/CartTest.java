package com.rafaelgalvezg.shop.model.cart;

import com.rafaelgalvezg.shop.model.product.Product;
import com.rafaelgalvezg.shop.model.product.TestProductFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.rafaelgalvezg.shop.model.cart.TestCartFactory.emptyCartForRandomCustomer;
import static com.rafaelgalvezg.shop.model.money.TestMoneyFactory.euros;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CartTest {

    @Test
    void givenEmptyCart_addTwoProducts_productsAreInCart() throws NotEnoughItemsInStockException{
        Cart cart = emptyCartForRandomCustomer();

        Product product1 = TestProductFactory.createTestProduct(euros(12, 99));
        Product product2 = TestProductFactory.createTestProduct(euros(5, 97));

        cart.addProduct(product1, 3);
        cart.addProduct(product2, 5);

        assertThat(cart.lineItems()).hasSize(2);
        assertThat(cart.lineItems().get(0).product()).isEqualTo(product1);
        assertThat(cart.lineItems().get(0).quantity()).isEqualTo(3);
        assertThat(cart.lineItems().get(1).product()).isEqualTo(product2);
        assertThat(cart.lineItems().get(1).quantity()).isEqualTo(5);
    }

    @Test
    @DisplayName("given Empty Cart, adding two products, validate number of Items")
    void givenEmptyCart2 () throws NotEnoughItemsInStockException {
        Cart cart = emptyCartForRandomCustomer();
        Product product1 = TestProductFactory.createTestProduct(euros(12, 99));
        Product product2 = TestProductFactory.createTestProduct(euros(5, 97));

        cart.addProduct(product1, 3);
        cart.addProduct(product2, 5);

        assertThat(cart.numberOfItems()).isEqualTo(8);
        assertThat(cart.subTotal()).isEqualTo(euros(68,82));
    }

    @Test
    @DisplayName("given a product with a few items available exception")
    void givenAProduct1 () {
        Cart cart = emptyCartForRandomCustomer();
        Product product = TestProductFactory.createTestProduct(euros(9, 97), 3);

        ThrowingCallable invocation = () -> cart.addProduct(product, 4);

        assertThatExceptionOfType(NotEnoughItemsInStockException.class)
                .isThrownBy(invocation)
                .satisfies(ex -> assertThat(ex.itemInStock()).isEqualTo(product.itemInStock()));
    }

    @Test
    @DisplayName("given a product with a few items available succeeds")
    void givenAProduct2 () {
        Cart cart = emptyCartForRandomCustomer();
        Product product = TestProductFactory.createTestProduct(euros(9, 97), 3);

        ThrowingCallable invocation = () -> cart.addProduct(product, 3);

        assertThatNoException().isThrownBy(invocation);
    }

    @ParameterizedTest
    @ValueSource(ints = {-100, -1, 0})
    @DisplayName("given empty cart add less that one item of a product")
    void givenAProduct3 (int quantity) {
        Cart cart = emptyCartForRandomCustomer();
        Product product = TestProductFactory.createTestProduct(euros(9, 97));

        assertThrows(IllegalArgumentException.class, () -> cart.addProduct(product, quantity));
    }
}
