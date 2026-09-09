package com.niva.shopcartpro.controller;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createProduct_shouldReturnCreatedProduct() throws Exception {

        String requestJson = """
                {
                    "name": "Keyboard",
                    "price": 59.99
                }
                """;

        mockMvc.perform(post("/products")
                .contentType("application/json")
                .content(requestJson))
                .andExpect(status().isCreated());
    }

}