package com.rafaelgalvezg.shop.adapter.in.rest.cart;

import com.rafaelgalvezg.shop.application.port.in.cart.AddToCartUseCase;
import com.rafaelgalvezg.shop.application.port.in.cart.EmptyCartUseCase;
import com.rafaelgalvezg.shop.application.port.in.cart.GetCartUseCase;
import com.rafaelgalvezg.shop.application.port.in.cart.ProductNotFoundException;
import com.rafaelgalvezg.shop.model.cart.Cart;
import com.rafaelgalvezg.shop.model.cart.NotEnoughItemsInStockException;
import com.rafaelgalvezg.shop.model.customer.CustomerId;
import com.rafaelgalvezg.shop.model.product.Product;
import com.rafaelgalvezg.shop.model.product.ProductId;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static com.rafaelgalvezg.shop.adapter.in.rest.HttpTestCommons.assertThatResponseIsError;
import static com.rafaelgalvezg.shop.adapter.in.rest.cart.CartsControllerAssertions.assertThatResponseIsCart;
import static com.rafaelgalvezg.shop.model.money.TestMoneyFactory.euros;
import static com.rafaelgalvezg.shop.model.product.TestProductFactory.createTestProduct;
import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CartsControllerTest {

    private static final CustomerId TEST_CUSTOMER_ID = new CustomerId(61157);
    private static final Product TEST_PRODUCT_1 = createTestProduct(euros(19, 99));
    private static final Product TEST_PRODUCT_2 = createTestProduct(euros(25, 99));

    @LocalServerPort
    private int testPort;

    @MockBean
    AddToCartUseCase addToCartUseCase;
    @MockBean
    GetCartUseCase getCartUseCase;
    @MockBean
    EmptyCartUseCase emptyCartUseCase;


    @Test
    void givenASyntacticallyInvalidCustomerId_getCart_returnsAnError() {
        String customerId = "foo";

        Response response = given().port(testPort).get("/carts/" + customerId).then().extract().response();

        assertThatResponseIsError(response, BAD_REQUEST, "Invalid 'customerId'");
    }

    @Test
    void givenAValidCustomerIdAndACart_getCart_requestsCartFromUseCaseAndReturnsIt() throws NotEnoughItemsInStockException {
        CustomerId customerId = TEST_CUSTOMER_ID;

        Cart cart = new Cart(customerId);
        cart.addProduct(TEST_PRODUCT_1, 3);
        cart.addProduct(TEST_PRODUCT_2, 5);

        when(getCartUseCase.getCart(customerId)).thenReturn(cart);

        Response response =
                given().port(testPort).get("/carts/" + customerId.value()).then().extract().response();

        assertThatResponseIsCart(response, cart);
    }

    @Test
    void givenSomeTestData_addLineItem_invokesAddToCartUseCaseAndReturnsUpdatedCart() throws NotEnoughItemsInStockException, ProductNotFoundException {
        CustomerId customerId = TEST_CUSTOMER_ID;
        ProductId productId = TEST_PRODUCT_1.id();
        int quantity = 5;

        Cart cart = new Cart(customerId);
        cart.addProduct(TEST_PRODUCT_1, quantity);

        when(addToCartUseCase.addToCart(customerId, productId, quantity)).thenReturn(cart);

        Response response =
                given()
                        .port(testPort)
                        .queryParam("productId", productId.value())
                        .queryParam("quantity", quantity)
                        .post("/carts/" + customerId.value() + "/line-items")
                        .then()
                        .extract()
                        .response();

        assertThatResponseIsCart(response, cart);
    }

    @Test
    void givenAnInvalidProductId_addLineItem_returnsAnError() {
        String productId = "";
        int quantity = 5;

        Response response =
                given()
                        .port(testPort)
                        .queryParam("productId", productId)
                        .queryParam("quantity", quantity)
                        .post("/carts/" + TEST_CUSTOMER_ID.value() + "/line-items")
                        .then()
                        .extract()
                        .response();

        assertThatResponseIsError(response, BAD_REQUEST, "Invalid 'productId'");
    }

    @Test
    void givenProductNotFound_addLineItem_returnsAnError() throws NotEnoughItemsInStockException, ProductNotFoundException {
        ProductId productId = ProductId.randomProductId();
        int quantity = 5;

        when(addToCartUseCase.addToCart(TEST_CUSTOMER_ID, productId, quantity))
                .thenThrow(new ProductNotFoundException());

        Response response =
                given()
                        .port(testPort)
                        .queryParam("productId", productId.value())
                        .queryParam("quantity", quantity)
                        .post("/carts/" + TEST_CUSTOMER_ID.value() + "/line-items")
                        .then()
                        .extract()
                        .response();

        assertThatResponseIsError(response, BAD_REQUEST, "The requested product does not exist");
    }

    @Test
    void givenNotEnoughItemsInStock_addLineItem_returnsAnError() throws NotEnoughItemsInStockException, ProductNotFoundException {
        ProductId productId = ProductId.randomProductId();
        int quantity = 5;

        when(addToCartUseCase.addToCart(TEST_CUSTOMER_ID, productId, quantity))
                .thenThrow(new NotEnoughItemsInStockException("Not enough items in stock", 2));

        Response response =
                given()
                        .port(testPort)
                        .queryParam("productId", productId.value())
                        .queryParam("quantity", quantity)
                        .post("/carts/" + TEST_CUSTOMER_ID.value() + "/line-items")
                        .then()
                        .extract()
                        .response();

        assertThatResponseIsError(response, BAD_REQUEST, "Only 2 items in stock");
    }

    @Test
    void givenACustomerId_deleteCart_invokesDeleteCartUseCaseAndReturnsUpdatedCart() {
        given()
                .port(testPort)
                .delete("/carts/" + TEST_CUSTOMER_ID.value())
                .then()
                .statusCode(NO_CONTENT.value());

        verify(emptyCartUseCase).emptyCart(TEST_CUSTOMER_ID);
    }
}