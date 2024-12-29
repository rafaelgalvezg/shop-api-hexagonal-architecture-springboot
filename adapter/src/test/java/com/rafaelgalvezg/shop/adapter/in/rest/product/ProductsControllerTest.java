package com.rafaelgalvezg.shop.adapter.in.rest.product;

import com.rafaelgalvezg.shop.application.port.in.product.FindProductsUseCase;
import com.rafaelgalvezg.shop.model.product.Product;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;


import static com.rafaelgalvezg.shop.adapter.in.rest.HttpTestCommons.assertThatResponseIsError;
import static com.rafaelgalvezg.shop.adapter.in.rest.product.ProductsControllerAssertions.assertThatResponseIsProductList;
import static com.rafaelgalvezg.shop.model.money.TestMoneyFactory.euros;
import static com.rafaelgalvezg.shop.model.product.TestProductFactory.createTestProduct;
import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductsControllerTest {

    private static final Product TEST_PRODUCT_1 = createTestProduct(euros(19, 99));
    private static final Product TEST_PRODUCT_2 = createTestProduct(euros(25, 99));

    private String token;

    @MockBean
    FindProductsUseCase findProductsUseCase;

    @LocalServerPort
    private int testPort;

    @BeforeEach
    void loadToken() {
        token = given()
                .port(testPort)
                .param("grant_type", "password")
                .param("realm", "spring-keycloak-realm")
                .param("client_id", "spring-keycloak-client")
                .param("username", "admin")
                .param("password", "admin")
                .param("client_secret", "iKCL0NjcJKRUUriAlxNYRmZBlyQsj6f8")
                .param("Content-Type", "application/x-www-form-urlencoded")
                .when()
                .post("http://localhost:8383/realms/spring-keycloak-realm/protocol/openid-connect/token")
                .then()
                .extract()
                .jsonPath()
                .get("access_token");
    }

    @Test
    void givenAQueryAndAListOfProducts_findProducts_requestsProductsViaQueryAndReturnsThem() {
        String query = "foo";
        List<Product> productList = List.of(TEST_PRODUCT_1, TEST_PRODUCT_2);

        when(findProductsUseCase.findByNameOrDescription(query)).thenReturn(productList);

        Response response =
                given()
                        .port(testPort)
                        .header("Authorization", "Bearer " + token)
                        .queryParam("query", query)
                        .get("/products")
                        .then()
                        .extract()
                        .response();

        assertThatResponseIsProductList(response, productList);
    }

    @Test
    void givenANullQuery_findProducts_returnsError() {
        Response response = given()
                .port(testPort)
                .header("Authorization", "Bearer " + token)
                .get("/products").then().
                extract()
                .response();

        assertThatResponseIsError(response, BAD_REQUEST, "Missing 'query'");
    }

    @Test
    void givenATooShortQuery_findProducts_returnsError() {
        String query = "e";
        when(findProductsUseCase.findByNameOrDescription(query))
                .thenThrow(IllegalArgumentException.class);

        Response response =
                given()
                        .port(testPort)
                        .header("Authorization", "Bearer " + token)
                        .queryParam("query", query)
                        .get("/products")
                        .then()
                        .extract()
                        .response();

        assertThatResponseIsError(response, BAD_REQUEST, "Invalid 'query'");
    }
}