package com.phoenix.phoenix.service.cart;

import com.phoenix.phoenix.data.dto.CartRequestDto;
import com.phoenix.phoenix.data.dto.CartResponseDto;
import com.phoenix.phoenix.data.dto.CartUpdateDto;
import com.phoenix.phoenix.data.dto.QuantityOperation;
import com.phoenix.phoenix.data.models.AppUser;
import com.phoenix.phoenix.data.models.Cart;
import com.phoenix.phoenix.data.models.Item;
import com.phoenix.phoenix.data.repository.AppUserRepository;
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

    @Autowired
    AppUserRepository appUserRepository;

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

    @Test
    public void testCanUpdateCart(){

        CartUpdateDto updateDto = CartUpdateDto.builder()
                .itemId(13L)
                .userId(5005L)
                .quantityOperation(QuantityOperation.INCREASE)
                .build();

        AppUser user = appUserRepository.findById(updateDto.getUserId()).orElse(null);
        assertThat(user).isNotNull();
        Cart cart =user.getCart();

        Item item = null;
        for (int index = 0; index < cart.getItemList().size(); index ++){
            item = cart.getItemList().get(index);
            if ( item.getId() == updateDto.getItemId() ){
                break;
            }
        }
        assertThat(item).isNotNull();
        assertThat(item.getQuantityAdded()).isEqualTo(3);

        log.info("Cart update {}", updateDto);
        CartResponseDto responseDto = cartService.updateCartItem(updateDto);


        //get the item

        for ( int index = 0 ; index < responseDto.getItemList().size(); index++){
            item = responseDto.getItemList().get(index);
            if ( item.getId() == updateDto.getItemId() );
            break;
        }


        assertThat(item).isNotNull();
        assertThat(item.getQuantityAdded()).isEqualTo(4);
    }

}