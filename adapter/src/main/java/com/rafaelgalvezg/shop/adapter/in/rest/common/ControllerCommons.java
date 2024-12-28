package com.rafaelgalvezg.shop.adapter.in.rest.common;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ControllerCommons {
    private ControllerCommons(){}

    public static ClientErrorException clientErrorException(HttpStatus status, String message) {
        return new ClientErrorException(errorResponse(status, message));
    }

    public static ResponseEntity<ErrorEntity> errorResponse(HttpStatus status, String message) {
        ErrorEntity errorEntity = new ErrorEntity(status.value(), message);
        return ResponseEntity.status(status.value()).body(errorEntity);
    }
}
