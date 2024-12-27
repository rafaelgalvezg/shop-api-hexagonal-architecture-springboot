package com.rafaelgalvezg.shop.adapter.in.rest.common;

import com.rafaelgalvezg.shop.model.customer.CustomerId;
import jakarta.ws.rs.core.Response;

import static com.rafaelgalvezg.shop.adapter.in.rest.common.ControllerCommons.clientErrorException;

public class CustomerIdParser {
    private CustomerIdParser(){}

    public static CustomerId parseCustomerId(String string){
        try{
            return new CustomerId(Integer.parseInt(string));
        }catch (IllegalArgumentException e){
            throw clientErrorException(Response.Status.BAD_REQUEST, "Invalid 'customerId'");
        }
    }
}
