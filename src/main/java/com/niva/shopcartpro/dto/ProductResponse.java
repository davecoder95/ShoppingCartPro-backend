package com.niva.shopcartpro.dto;

public class ProductResponse {

    private Long id;
    private String name;
    private double price;

    public ProductResponse(Long id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}