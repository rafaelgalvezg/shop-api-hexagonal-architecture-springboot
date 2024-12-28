package com.rafaelgalvezg.shop.adapter.in.rest.cart;

import com.rafaelgalvezg.shop.application.port.in.cart.AddToCartUseCase;
import com.rafaelgalvezg.shop.application.port.in.cart.ProductNotFoundException;
import com.rafaelgalvezg.shop.model.cart.Cart;
import com.rafaelgalvezg.shop.model.cart.NotEnoughItemsInStockException;
import com.rafaelgalvezg.shop.model.customer.CustomerId;
import com.rafaelgalvezg.shop.model.product.ProductId;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.rafaelgalvezg.shop.adapter.in.rest.common.ControllerCommons.clientErrorException;
import static com.rafaelgalvezg.shop.adapter.in.rest.common.CustomerIdParser.parseCustomerId;
import static com.rafaelgalvezg.shop.adapter.in.rest.common.ProductIdParser.parseProductId;

@RequiredArgsConstructor
@RestController
@RequestMapping("/carts")
public class AddToCartController {

    private final AddToCartUseCase addToCartUseCase;

    @PostMapping("/{customerId}/line-items")
    public CartWebModel addLineItem(
            @PathVariable("customerId") String customerIdString,
            @RequestParam("productId") String productIdString,
            @RequestParam("quantity") int quantity){
        CustomerId customerId = parseCustomerId(customerIdString);
        ProductId productId = parseProductId(productIdString);

        try{
            Cart cart = addToCartUseCase.addToCart(customerId, productId, quantity);
            return CartWebModel.fromDomainModel(cart);
        }catch (ProductNotFoundException e){
            throw clientErrorException(
                    HttpStatus.BAD_REQUEST, "The requested product does not exist");
        }catch (NotEnoughItemsInStockException e){
            throw clientErrorException(
                    HttpStatus.BAD_REQUEST, "Only %d items in stock".formatted(e.itemInStock()));
        }
    }
}
