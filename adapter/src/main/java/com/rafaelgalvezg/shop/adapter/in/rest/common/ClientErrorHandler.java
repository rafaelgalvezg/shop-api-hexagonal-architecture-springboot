package com.rafaelgalvezg.shop.adapter.in.rest.common;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ClientErrorHandler {

    @ExceptionHandler(ClientErrorException.class)
    public ResponseEntity<ErrorEntity> handleClientErrorException(ClientErrorException e){
        return e.getResponse();
    }

}
