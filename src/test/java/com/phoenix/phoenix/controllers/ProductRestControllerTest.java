package com.phoenix.phoenix.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phoenix.phoenix.data.models.Product;
import com.phoenix.phoenix.data.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

//import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"/db/insert.sql"})

class ProductRestControllerTest {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    public void findAllProducts() throws Exception {
         mockMvc.perform(get("/api/product")
                .contentType("application/json"))
                .andExpect(status().is(200))
                .andDo(print());

    }

    @Test
    public void createProduct() throws Exception {
        Product product = new Product();
        product.setName("Mymy");
        product.setPrice(550
        );
        product.setDescription("A detergent by Bimbo");
        product.setQuantity(10);
        product.setImageUrl("product.detergent.bimbo");

        String requestBody = mapper.writeValueAsString(product);
        mockMvc.perform(post("/api/product")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().is(200))
                .andDo(print());
    }

    @Test
    public void updateProduct() throws Exception {
        Product product = repository.findById(12L).orElse(null);
        assertThat(product).isNotNull();

        mockMvc.perform(patch("/api/product/12").
                contentType("application/json-patch+json").
                content(Files.readAllBytes(Path.of("payload.json")))).
                andExpect(status().is(200)).
                andDo(print());

        product = repository.findById(12L).orElse(null);
        assertThat(product).isNotNull();
        assertThat(product.getName()).isEqualTo("Okin");
    }
}