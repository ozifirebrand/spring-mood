package com.phoenix.phoenix.data.repository;

import com.phoenix.phoenix.data.models.Cart;
import com.phoenix.phoenix.data.models.Item;
import com.phoenix.phoenix.data.models.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Sql(scripts = {"/db/insert.sql"})
class CartRepositoryTest {

    @Autowired
    private ProductRepository repository;

    @BeforeEach
    void setUp() {
    }

    @Test
    public void addProductToCart(){
        Product product = repository.findById(12L).orElse(null);
        assertThat(product).isNotNull();
        Item item = new Item(product, 2);
        Cart cart  = new Cart();
        cart.addItem(item);
    }


}