package com.ecommerce.cart.controller;

import com.ecommerce.cart.entity.Cart;
import com.ecommerce.cart.service.CartService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(path = "/api")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping(path = "/showCart/{userId}")
    public String showCart(@PathVariable int userId){
        return String.valueOf(cartService.productsInCart(userId));
    }

    @PostMapping(path = "/addToCart/{userId}/{productId}")
    public String addToCart(@PathVariable int userId, @PathVariable int productId) {
        return cartService.addToCart(userId, productId);
    }

    @DeleteMapping(path = "/removeToCart/{userId}")
    public String removeToCart(@PathVariable int userId, @RequestBody Cart cart) {
        return cartService.removeToCart(userId, cart);
    }


}
