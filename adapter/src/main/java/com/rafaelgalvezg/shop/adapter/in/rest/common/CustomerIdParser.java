package com.rafaelgalvezg.shop.adapter.in.rest.common;

import com.rafaelgalvezg.shop.model.customer.CustomerId;
import org.springframework.http.HttpStatus;

import static com.rafaelgalvezg.shop.adapter.in.rest.common.ControllerCommons.clientErrorException;

public class CustomerIdParser {
    private CustomerIdParser(){}

    public static CustomerId parseCustomerId(String string){
        try{
            return new CustomerId(Integer.parseInt(string));
        }catch (IllegalArgumentException e){
            throw clientErrorException(HttpStatus.BAD_REQUEST, "Invalid 'customerId'");
        }
    }
}
