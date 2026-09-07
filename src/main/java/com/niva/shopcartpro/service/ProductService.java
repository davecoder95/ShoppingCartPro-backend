
package com.niva.shopcartpro.service;

import com.niva.shopcartpro.dto.ProductRequest;
import com.niva.shopcartpro.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final List<Product> products = new ArrayList<>(
            List.of(
                    new Product(1L, "Laptop", 999.99),
                    new Product(2L, "Keyboard", 79.99),
                    new Product(3L, "Mouse", 39.99)));

    public List<Product> getProducts() {
        return products;
    }

    public Product getProductById(Long id) {

        for (Product product : products) {

            if (product.getId().equals(id)) {
                return product;
            }
        }

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Product not found");
    }

    public Product createProduct(ProductRequest request) {

        Product product = new Product();

        product.setId(generateProductId());
        product.setName(request.getName());
        product.setPrice(request.getPrice());

        products.add(product);

        return product;
    }

    public Product updateProduct(Long id, ProductRequest request) {

        for (Product product : products) {

            if (product.getId().equals(id)) {

                product.setName(request.getName());
                product.setPrice(request.getPrice());

                return product;
            }
        }

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Product not found");
    }

    public void deleteProduct(Long id) {

        for (Product product : products) {

            if (product.getId().equals(id)) {
                products.remove(product);
                return;
            }
        }

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Product not found");
    }

    private Long generateProductId() {

        return products.stream()
                .mapToLong(Product::getId)
                .max()
                .orElse(0) + 1;
    }
}
