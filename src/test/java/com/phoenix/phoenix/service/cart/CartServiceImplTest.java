package com.phoenix.phoenix.service.cart;

import com.phoenix.phoenix.data.dto.CartRequestDto;
import com.phoenix.phoenix.data.dto.CartResponseDto;
import com.phoenix.phoenix.web.exceptions.BusinessLogicException;
import com.phoenix.phoenix.web.exceptions.ProductDoesNotExistException;
import com.phoenix.phoenix.web.exceptions.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.*;
@SpringBootTest
@Sql(scripts = {"/db/insert.sql"})
@Slf4j
class CartServiceImplTest {

    @Autowired
    CartService cartService;

    @Test
    public void addItemToCart() throws UserNotFoundException, ProductDoesNotExistException, BusinessLogicException {
        CartRequestDto cartRequestDto = new CartRequestDto();
        cartRequestDto.setProductId(13L);
        cartRequestDto.setQuantity(1);
        cartRequestDto.setUserId(5010L);

        CartResponseDto cartResponseDto = cartService.addItemToCart(cartRequestDto);
        assertThat(cartResponseDto.getItemList()).isNotNull();
        assertThat(cartResponseDto.getItemList().size()).isEqualTo(1);
    }

    @Test
    public void priceHasBeenUpdated() throws UserNotFoundException, ProductDoesNotExistException, BusinessLogicException {
        assertThat(cartService.viewCart(345L).getTotalPrice()).isEqualTo(0);
        CartRequestDto cartRequestDto = new CartRequestDto();
        cartRequestDto.setProductId(13L);
        cartRequestDto.setQuantity(2);
        cartRequestDto.setUserId(5010L);

        CartResponseDto cartResponseDto = cartService.addItemToCart(cartRequestDto);
        log.info("Cart is {}", cartService.viewCart(345L));
        log.info("Total price of cart ");
        assertThat(cartResponseDto.getTotalPrice()).isEqualTo(1000);
    }

}