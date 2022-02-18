package com.phoenix.phoenix.service.cart;

import com.github.fge.jsonpatch.JsonPatch;
import com.phoenix.phoenix.data.dto.CartRequestDto;
import com.phoenix.phoenix.data.dto.CartResponseDto;
import com.phoenix.phoenix.data.dto.CartUpdateDto;
import com.phoenix.phoenix.data.models.Cart;
import com.phoenix.phoenix.web.exceptions.BusinessLogicException;

public interface CartService {
    CartResponseDto addItemToCart(CartRequestDto cartRequestDto) throws BusinessLogicException;
    CartResponseDto viewCart(Long userId) throws BusinessLogicException;
    CartResponseDto updateCartItem(CartUpdateDto cartUpdateDto) throws BusinessLogicException;
}

