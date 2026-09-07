package com.niva.shopcartpro.repository;

import com.niva.shopcartpro.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}