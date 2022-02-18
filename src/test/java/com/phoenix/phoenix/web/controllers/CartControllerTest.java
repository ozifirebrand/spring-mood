package com.phoenix.phoenix.web.controllers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.phoenix.phoenix.data.dto.CartRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void addItemToCart() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        CartRequestDto requestDto = new CartRequestDto();
        requestDto.setQuantity(1);
        requestDto.setProductId(11L);
        requestDto.setUserId(5005L);

        mockMvc.perform(post("/api/cart")
                .contentType("application/json")
                .content(objectMapper
                        .writeValueAsString(requestDto)))
                .andExpect(status().is(200))
                .andDo(print());
    }
}