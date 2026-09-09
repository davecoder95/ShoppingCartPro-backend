package com.niva.shopcartpro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.niva.shopcartpro.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}