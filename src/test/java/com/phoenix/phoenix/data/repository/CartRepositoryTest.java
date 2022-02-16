package com.phoenix.phoenix.data.repository;

import com.phoenix.phoenix.data.models.Cart;
import com.phoenix.phoenix.data.models.Item;
import com.phoenix.phoenix.data.models.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
@Sql(scripts = {"/db/insert.sql"})//This script is used for test purposes
class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;
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
        cartRepository.save(cart);
        assertThat(cart.getId()).isNotNull();
        assertThat(cart.getItemList().isEmpty()).isFalse();
        assertThat(cart.getItemList().get(0).getProduct()).isNotNull();

    }

    @Test
    public void viewItemsInCartTest(){
        //get a cart by Id
        Cart savedCart = cartRepository.findById(345L).orElse(null);
        assertThat(savedCart).isNotNull();
        assertThat(savedCart.getItemList().size()).isEqualTo(3);
    }


}