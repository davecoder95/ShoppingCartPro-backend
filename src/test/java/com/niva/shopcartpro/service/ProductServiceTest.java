package com.niva.shopcartpro.service;

import com.niva.shopcartpro.model.Product;
import com.niva.shopcartpro.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void getProducts_shouldReturnProducts() {

        List<Product> products = List.of(
                new Product(1L, "Laptop", BigDecimal.valueOf(999.99)),
                new Product(2L, "Mouse", BigDecimal.valueOf(39.99)));

        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.getProducts();

        assertEquals(2, result.size());
        assertEquals("Laptop", result.get(0).getName());
        assertEquals("Mouse", result.get(1).getName());
    }

    @Test
    void getProductById_shouldReturnProduct() {

        Product product = new Product(
                1L,
                "Laptop",
                BigDecimal.valueOf(999.99));

        when(productRepository.findById(1L))
                .thenReturn(java.util.Optional.of(product));

        Product result = productService.getProductById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Laptop", result.getName());
        assertEquals(BigDecimal.valueOf(999.99), result.getPrice());
    }

    @Test
    void getProductById_shouldThrowExceptionWhenProductNotFound() {

        when(productRepository.findById(99L))
                .thenReturn(java.util.Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> productService.getProductById(99L));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
}