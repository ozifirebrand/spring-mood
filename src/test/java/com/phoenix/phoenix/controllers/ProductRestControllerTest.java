package com.phoenix.phoenix.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phoenix.phoenix.data.models.Product;
import com.phoenix.phoenix.data.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

//import static org.junit.jupiter.api.Assertions.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"/db/insert.sql"})

class ProductRestControllerTest {

    @Autowired
    private ProductRepository repository;

    private Model Model;
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
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/api/product")
                .param("name", "Moto car")
                .param("description", "Nice")
                .param("price", "540.8")
                .param("quantity", "5");


        mockMvc.perform(request
                .contentType("multipart/form-data"))
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