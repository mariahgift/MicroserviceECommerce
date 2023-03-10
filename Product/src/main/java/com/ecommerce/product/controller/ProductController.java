package com.ecommerce.product.controller;

import com.ecommerce.product.entity.Product;
import com.ecommerce.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping(path = "/getAllProducts")
    public List<Product> getProducts() {
        return productService.findAllProducts();
    }

    @GetMapping(path = "/getProductById/{productId}")
    public Product getProductById(@PathVariable int productId) {
        return productService.findProductById(productId);
    }


    @PostMapping(path = "/addProduct")
    public String addUser(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @PutMapping(path = "/updateProduct/{productId}/{userId}")
    public String updateProductById(@PathVariable int productId, @PathVariable int userId, @RequestBody Product product) {
        product.setProductId(productId);
        return productService.updateProductById(productId, userId, product);
    }

    @DeleteMapping(path = "/deleteProduct/{productId}/{userId}")
    public String deleteProduct(@PathVariable int productId, @PathVariable int userId) {
        return productService.deleteProduct(productId, userId);
    }
}
