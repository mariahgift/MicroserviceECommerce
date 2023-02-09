package com.ecommerce.cart.controller;

import com.ecommerce.cart.entity.Cart;
import com.ecommerce.cart.service.CartService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping(path = "/addToCart/{userId}/{productId}")
    public String addToCart(@PathVariable int userId, @PathVariable int productId) {
        return cartService.addToCart(userId, productId);
    }

    @DeleteMapping(path = "/removeToCart/{userId}/{productId}")
    public String removeToCart(@PathVariable int userId, @PathVariable int productId) {
        return cartService.removeToCart(userId, productId);
    }
}
