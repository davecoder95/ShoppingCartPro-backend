package com.niva.shopcartpro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.niva.shopcartpro.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}