package com.rafaelgalvezg.shop.adapter.in.rest.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@Slf4j
@RestControllerAdvice
public class ClientErrorHandler {

    @ExceptionHandler(ClientErrorException.class)
    public ResponseEntity<ErrorEntity> handleClientErrorException(ClientErrorException e){
        return e.getResponse();
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorEntity> handleAccessDeniedException(AccessDeniedException e){
        ErrorEntity errorEntity = new ErrorEntity(HttpStatus.FORBIDDEN.value(), "You are not have permission to access this resource");
        log.error("Access denied", e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorEntity);
    }




}
