package com.ecommerce.cart.service;

import com.ecommerce.cart.dto.Product;
import com.ecommerce.cart.dto.User;
import com.ecommerce.cart.entity.Cart;
import com.ecommerce.cart.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    RestTemplate restTemplate;

    public String addToCart(int userId, int productId) {
        if (getUserId(userId) != 0 && getProductId(productId) != 0 && getUserRole(userId).equals("buyer")) {
            Cart addProduct = new Cart();
            addProduct.setUserId(userId);
            addProduct.setProductId(productId);
            cartRepository.save(addProduct);
            return "Product added to cart.";
        }
        else if (getUserId(userId) == 0) {
            return "User not found";
        } else if (getProductId(productId) == 0) {
            return "Product not found";
        } else if (getUserRole(userId).equals("seller")) {
            return "Seller can't add product to the cart";
        } else {
            return "Error occurred";
        }
    }


    public String removeToCart(int userId, int productId) {
        if (getUserId(userId) != 0 && getProductId(productId) != 0 && getUserRole(userId).equals("buyer")) {
            Cart removeCart = new Cart();
            removeCart.setUserId(userId);
            removeCart.setProductId(productId);
            cartRepository.delete(removeCart);
            return "Product remove from the cart.";
        }  else if (getUserId(userId) == 0) {
            return "User not found";
        } else if (getProductId(productId) == 0) {
            return "Product not found";
        } else if (getUserRole(userId).equals("seller")) {
            return "Seller can't add product to the cart";
        } else {
            return "Error occurred";
        }
    }

    public String getUserRole(int userId) {
        User getFromUserAPI = restTemplate.getForObject("http://localhost:8080/api/getUserById/"+userId, User.class);
        return getFromUserAPI.getRole();
    }

    public int getUserId(int userId) {
        User getFromUserAPI =  restTemplate.getForObject("http://localhost:8080/api/getUserById/"+userId, User.class);
        return getFromUserAPI.getUserId();
    }

    public int getProductId(int productId) {
        Product getFromProductAPI = restTemplate.getForObject("http://localhost:8081/api/getProductById/"+productId, Product.class);
        return getFromProductAPI.getProductId();
    }


}
