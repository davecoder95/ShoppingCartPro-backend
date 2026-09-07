
package com.niva.shopcartpro.service;

import com.niva.shopcartpro.repository.ProductRepository;
import com.niva.shopcartpro.dto.ProductRequest;
import com.niva.shopcartpro.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProductService {

    /*
     * private final List<Product> products = new ArrayList<>(
     * List.of(
     * new Product(1L, "Laptop", BigDecimal.valueOf(999.99)),
     * new Product(2L, "Keyboard", BigDecimal.valueOf(79.99)),
     * new Product(3L, "Mouse", BigDecimal.valueOf(39.99))
     * ));
     */
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {

        return productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Product not found"));
    }

    public Product createProduct(ProductRequest request) {

        Product product = new Product();

        product.setName(request.getName());
        product.setPrice(request.getPrice());

        return productRepository.save(product);

    }

    public Product updateProduct(Long id, ProductRequest request) {

        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Product not found");
        }

        product.setName(request.getName());
        product.setPrice(request.getPrice());

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {

        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Product not found");
        }

        productRepository.delete(product);
    }

}
