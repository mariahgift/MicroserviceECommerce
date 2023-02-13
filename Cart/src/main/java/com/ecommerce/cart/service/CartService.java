package com.ecommerce.cart.service;

import com.ecommerce.cart.dto.Product;
import com.ecommerce.cart.dto.User;
import com.ecommerce.cart.entity.Cart;
import com.ecommerce.cart.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;


@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    RestTemplate restTemplate;

    public String addToCart(int userId, int productId) {
        if (getUserId(userId) != null && getProductId(productId) != null && getUserRole(userId).equals("buyer")) {
            Cart addProduct = new Cart();
            addProduct.setUserId(userId);
            addProduct.setProductId(productId);
            cartRepository.save(addProduct);
            return "Product added to cart.";
        }
        else if (getUserId(userId) == null) {
            return "User not found";
        } else if (getProductId(productId) == null) {
            return "Product not found";
        } else if (getUserRole(userId).equals("seller")) {
            return "Seller can't add product to the cart";
        } else {
            return "Error occurred";
        }
    }

    public String removeToCart(int userId, Cart cart) {
        if (getUserId(userId) != null && cart.getProductId() != 0 && getUserRole(userId).equals("buyer")) {
            Cart removeCart = new Cart();
            removeCart.setCartId(cart.getCartId());
            removeCart.setUserId(cart.getUserId());
            removeCart.setProductId(cart.getProductId());
            cartRepository.delete(removeCart);
            return "Product remove from the cart.";
        }  else if (getUserId(cart.getUserId()) == null) {
            return "User not found";
        }  else if (getUserRole(userId).equals("seller")) {
            return "Seller can't delete product to the cart";
        } else {
            return "Error occurred";
        }
    }

    public String getUserRole(int userId) {
        User getFromUserAPI = restTemplate.getForObject("http://localhost:8080/api/getUserById/"+userId, User.class);
        return getFromUserAPI.getRole();
    }

    public String getUserId(int userId) {
        User getFromUserAPI =  restTemplate.getForObject("http://localhost:8080/api/getUserById/"+userId, User.class);
        return String.valueOf(getFromUserAPI.getUserId());
    }

    public String getProductId(int productId) {
        Product getFromProductAPI = restTemplate.getForObject("http://localhost:8081/api/getProductById/"+productId, Product.class);
        return String.valueOf(getFromProductAPI.getProductId());
    }

    private List<Product> getProductInfo(){
        Product[] getExternalAPI = restTemplate.getForObject("http://localhost:8081/api/getAllProducts",Product[].class);
        if(getExternalAPI == null){
            return null;
        }
        return Arrays.asList(getExternalAPI);
    }

    public String productsInCart(int userId) {
        if(findByUserId(userId)){
            StringBuilder viewItems = new StringBuilder();
            for(int i = 0; i < getProductInfo().size(); i++){
                for(int j = 0; j < cartRepository.findByUserIdAndProductId(userId,getProductInfo().get(i).getProductId()).size(); j++){
                    if(checkItem(userId,getProductInfo().get(i).getProductId())){
                        viewItems.append(getProductInfo().get(i).getProductName()).append("\n");
                        viewItems.append(getProductInfo().get(i).getProductDescription()).append("\n");
                        viewItems.append(getProductInfo().get(i).getProductPrice()).append("\n\n");
                    }
                }
            }
            return viewItems.toString();
        } else{
            return "You don't have items in cart";
        }
    }

    private boolean findByUserId(int userId) {
        if(!(cartRepository.findByUserId(userId).isEmpty())) {
            return true;
        }
        else {
            return false;
        }
    }

    private boolean checkItem(int userId, int productId) {
        if(!(cartRepository.findByUserIdAndProductId(userId, productId).isEmpty())) {
            return true;
        }
        else {
            return false;
        }
    }
}
