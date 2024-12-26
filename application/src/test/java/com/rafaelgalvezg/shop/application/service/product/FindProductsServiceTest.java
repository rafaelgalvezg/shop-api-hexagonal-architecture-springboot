package com.rafaelgalvezg.shop.application.service.product;

import com.rafaelgalvezg.shop.application.port.out.persistence.ProductRepository;
import com.rafaelgalvezg.shop.model.product.Product;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.rafaelgalvezg.shop.model.money.TestMoneyFactory.euros;
import static com.rafaelgalvezg.shop.model.product.TestProductFactory.createTestProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FindProductsServiceTest {

    private static final Product TEST_PRODUCT_1 = createTestProduct(euros(19, 99));
    private static final Product TEST_PRODUCT_2 = createTestProduct(euros(25, 99));

    private final ProductRepository productRepository = mock(ProductRepository.class);
    private final FindProductsService findProductsService =
            new FindProductsService(productRepository);

    @Test
    @DisplayName("given a search query")
    void test1(){
        when(productRepository.findByNameOrDescription("one")).thenReturn(List.of(TEST_PRODUCT_1));
        when(productRepository.findByNameOrDescription("two")).thenReturn(List.of(TEST_PRODUCT_2));
        when(productRepository.findByNameOrDescription("one-tow"))
                .thenReturn(List.of(TEST_PRODUCT_1, TEST_PRODUCT_2));
        when(productRepository.findByNameOrDescription("empty")).thenReturn(List.of());

        assertThat(findProductsService.findByNameOrDescription("one")).containsExactly(TEST_PRODUCT_1);
        assertThat(findProductsService.findByNameOrDescription("two")).containsExactly(TEST_PRODUCT_2);
        assertThat(findProductsService.findByNameOrDescription("one-tow"))
                .containsExactly(TEST_PRODUCT_1, TEST_PRODUCT_2);
        assertThat(findProductsService.findByNameOrDescription("empty")).isEmpty();
    }

    @Test
    @DisplayName("given a too short search query")
    void test2(){
        String searchQuery = "x";
        ThrowableAssert.ThrowingCallable invocation =
                () -> findProductsService.findByNameOrDescription(searchQuery);

        assertThatIllegalArgumentException().isThrownBy(invocation);
    }
}
