package com.ecommerce.product.service;

import com.ecommerce.product.entity.Product;
import com.ecommerce.product.dto.User;
import com.ecommerce.product.exception.BadRequestException;
import com.ecommerce.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    RestTemplate restTemplate;

    public List<Object> getUsers() {
        String url = "http://localhost:8080/api/getAllUsers";
        Object[] users = restTemplate.getForObject(url, Object[].class);
        return Arrays.asList(users);
    }

    public String getUserRole(int userId) {
        User getFromExternalAPI = restTemplate.getForObject("http://localhost:8080/api/getUserById/" + userId, User.class);
        return getFromExternalAPI.getRole();
    }

    public int getUserId(int userId) {
        User getFromExternalAPI = restTemplate.getForObject("http://localhost:8080/api/getUserById/" + userId, User.class);
        return getFromExternalAPI.getUserId();
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Product findProductById(int productId) throws BadRequestException {
        return productRepository.findById(productId).orElseThrow(() -> new BadRequestException("Product not found"));
    }

    public String addProduct(Product product) {
        if (getUserRole(product.getUserId()).equals("seller")) {
            productRepository.save(product);
            return "Product successfully added";
        } else {
            return "You don't have access to add product.";
        }
    }

    public String updateProductById(int productId, int userId, Product product) {
        if (isProductExist(productId) && getUserRole(userId).equals("seller") && product.getUserId() == userId) {
            Product updateProduct = productRepository.findById(productId).orElseThrow();
            updateProduct.setProductName(product.getProductName());
            updateProduct.setProductDescription(product.getProductDescription());
            updateProduct.setProductPrice(product.getProductPrice());
            updateProduct.setProductQuantity(product.getProductQuantity());
            updateProduct.setUserId(product.getUserId());
            productRepository.save(updateProduct);
            return "Product updated successfully";
        }
        if (product.getUserId() != userId) {
            return "You cannot update this product because it does not belong to yours.";
        }
        //check
        if (getUserRole(userId).equals("buyer")) {
            return "You don't have access to this page. Only seller can add product.";
        }
        return "Product not found";
    }

    public boolean isProductExist(int productId) {
        if (findProductById(productId) != null) {
            return true;
        } else {
            return false;
        }
    }

    public String deleteProduct(int productId, int userId, Product product) {
        if (isProductExist(productId) && getUserRole(userId).equals("seller") && product.getUserId() == userId) {
            productRepository.deleteById(productId);
            return "Product deleted";
        }
        if (product.getUserId() != userId) {
            return "You cannot delete this product because it does not belong to yours.";
        }
        if (getUserRole(userId).equals("buyer")) {
            return "You don't have access to this page. Only seller can delete product.";
        }
        return null;
    }
}
