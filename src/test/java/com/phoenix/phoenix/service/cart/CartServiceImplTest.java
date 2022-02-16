package com.phoenix.phoenix.service.cart;

import com.phoenix.phoenix.data.dto.CartItemDto;
import com.phoenix.phoenix.data.models.AppUser;
import com.phoenix.phoenix.data.models.Cart;
import com.phoenix.phoenix.data.models.Item;
import com.phoenix.phoenix.data.models.Product;
import com.phoenix.phoenix.data.repository.AppUserRepository;
import com.phoenix.phoenix.data.repository.CartRepository;
import com.phoenix.phoenix.data.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.*;
@SpringBootTest
@Sql(scripts = {"/db/insert.sql"})
class CartServiceImplTest {

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartRepository repository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void addItemToCart(){
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setProductId(13L);
        cartItemDto.setQuantity(1);
        cartItemDto.setUserId(5010L);

        //check if user exists

        AppUser user = appUserRepository.findById(cartItemDto.getUserId()).orElse(null);
        assertThat(user).isNotNull();

        //get user cart
        Cart cart = user.getCart();
        assertThat(cart).isNotNull();

        //check user exists
        Product product = productRepository.findById(13L).orElse(null);
        assertThat(product).isNotNull();
        assertThat(product.getQuantity()).isGreaterThanOrEqualTo(cartItemDto.getQuantity());

        //add product to cart
        Item cartItem = new Item(product, cartItemDto.getQuantity());
        cart.addItem(cartItem);

        //save cart
        repository.save(cart);
        assertThat(cart.getItemList().size()).isEqualTo(1);

    }
}