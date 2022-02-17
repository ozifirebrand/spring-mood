package com.phoenix.phoenix.service.cart;

import com.phoenix.phoenix.data.dto.CartItemDto;

public interface CartService {
    public void addItemToCart(CartItemDto itemDto);
}
