package com.rafaelgalvezg.shop.adapter.in.rest;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static org.assertj.core.api.Assertions.assertThat;

public final class HttpTestCommons {

    public static final int TEST_PORT = 8082;

    private HttpTestCommons() {}

    public static void assertThatResponseIsError(
            Response response,
            jakarta.ws.rs.core.Response.Status expectedStatus,
            String expectedErrorMessage) {
        assertThat(response.getStatusCode()).isEqualTo(expectedStatus.getStatusCode());

        JsonPath json = response.jsonPath();

        assertThat(json.getInt("httpStatus")).isEqualTo(expectedStatus.getStatusCode());
        assertThat(json.getString("errorMessage")).isEqualTo(expectedErrorMessage);
    }
}