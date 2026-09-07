package com.niva.shopcartpro.controller;

import com.niva.shopcartpro.dto.ProductRequest;
import com.niva.shopcartpro.dto.ProductResponse;
import com.niva.shopcartpro.model.Product;
import com.niva.shopcartpro.service.ProductService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

   @PostMapping("/products")
@ResponseStatus(HttpStatus.CREATED)
public ProductResponse createProduct(@RequestBody ProductRequest request) {

    Product product = productService.createProduct(request);

    return new ProductResponse(
            product.getId(),
            product.getName(),
            product.getPrice()
    );
}

    @PutMapping("/products/{id}")
public ProductResponse updateProduct(
        @PathVariable Long id,
        @RequestBody ProductRequest request) {

    Product product = productService.updateProduct(id, request);

    return new ProductResponse(
            product.getId(),
            product.getName(),
            product.getPrice()
    );
}

    @DeleteMapping("/products/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}