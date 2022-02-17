package com.phoenix.phoenix.service.cart;

import com.phoenix.phoenix.data.dto.CartItemDto;
import com.phoenix.phoenix.data.dto.CartRequestDto;
import com.phoenix.phoenix.data.dto.CartResponseDto;
import com.phoenix.phoenix.data.models.Cart;
import com.phoenix.phoenix.web.exceptions.BusinessLogicException;
import com.phoenix.phoenix.web.exceptions.ProductDoesNotExistException;
import com.phoenix.phoenix.web.exceptions.UserNotFoundException;

public interface CartService {
    CartResponseDto addItemToCart(CartRequestDto cartRequestDto) throws UserNotFoundException, ProductDoesNotExistException, BusinessLogicException;
    Cart viewCart();
}
