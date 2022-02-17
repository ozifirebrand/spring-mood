package com.phoenix.phoenix.service.cart;

import com.phoenix.phoenix.data.dto.CartItemDto;
import com.phoenix.phoenix.web.exceptions.BusinessLogicException;
import com.phoenix.phoenix.web.exceptions.ProductDoesNotExistException;
import com.phoenix.phoenix.web.exceptions.UserNotFoundException;

public interface CartService {
    public void addItemToCart(CartItemDto itemDto) throws UserNotFoundException, ProductDoesNotExistException, BusinessLogicException;
}
