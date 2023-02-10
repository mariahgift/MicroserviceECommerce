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


    public String getUserRole(int userId) {
        User getFromExternalAPI = restTemplate.getForObject("http://localhost:8080/api/getUserById/" + userId, User.class);
        if (getFromExternalAPI == null) {
            return null;
        } else {
            return getFromExternalAPI.getRole();
        }
    }

    public String getUserId(int userId) {
        User getFromExternalAPI = restTemplate.getForObject("http://localhost:8080/api/getUserById/" + userId, User.class);
        if (getFromExternalAPI == null) {
            return null;
        } else {
            return String.valueOf(getFromExternalAPI.getUserId());
        }
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Product findProductById(int productId) throws BadRequestException {
        return productRepository.findById(productId).orElseThrow(() -> new BadRequestException("Product not found"));
    }

    public boolean isProductExist(int productId) {
        if (findProductById(productId) != null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isUserExist(int userId) {
        if (getUserId(userId) == null) {
            return false;
        } else {
            return true;
        }
    }

    public String addProduct(Product product) {
        if (getUserRole(product.getUserId()).equals("seller")) {
            productRepository.save(product);
            return "Product successfully added";
        } else if (isUserExist(product.getUserId()) && getUserRole(product.getUserId()).equals("buyer")) {
            return "You don't have access to add product.";
        }
        else {
            return "User does not exist";
        }
    }

    public String updateProductById(int productId, int userId, Product product) {
        if (isProductExist(product.getProductId())) {
            if (getUserRole(userId).equals("seller") && product.getUserId() == userId) {
                Product updateProduct = productRepository.findById(productId).orElseThrow();
                updateProduct.setProductName(product.getProductName());
                updateProduct.setProductDescription(product.getProductDescription());
                updateProduct.setProductPrice(product.getProductPrice());
                updateProduct.setProductQuantity(product.getProductQuantity());
                updateProduct.setUserId(product.getUserId());
                productRepository.save(updateProduct);
                return "Product updated successfully";
            } else if (getUserId(userId) == null) {
                return "User does not exist";
            } else if (getUserRole(userId).equals("buyer")) {
                return "You don't have access to this page. Only seller can add product.";
            } else if (product.getUserId() != userId) {
                return "You cannot update this product because it does not belong to yours.";
            }
        } else {
            return "Product does not exist";
        }
        return "Error occurred";
    }


    public String deleteProduct(int productId, int userId) {
        Product findProduct = findProductById(productId);

        if (isProductExist(productId)) {
            if (getUserRole(userId).equals("buyer") && findProduct.getUserId() == userId) {
                productRepository.delete(findProductById(productId));
                return "Product deleted";
            } else if (findProduct.getUserId() != userId) {
                return "You cannot delete this product because it does not belong to yours.";
            } else if (getUserRole(userId).equals("seller")) {
                return "You don't have access to this page. Only seller can delete product.";
            }
        } else {
            return "Product does not exist";
        }
        return "Error occurred";
    }
}
