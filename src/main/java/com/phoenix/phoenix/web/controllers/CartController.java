package com.phoenix.phoenix.web.controllers;

import com.phoenix.phoenix.data.dto.CartRequestDto;
import com.phoenix.phoenix.data.dto.CartResponseDto;
import com.phoenix.phoenix.service.cart.CartService;
import com.phoenix.phoenix.web.exceptions.BusinessLogicException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;


    @PostMapping
    public ResponseEntity<?> addItemToCart(@RequestBody CartRequestDto cartRequestDto){
        CartResponseDto cartResponseDto ;
        try{
            cartResponseDto = cartService.addItemToCart(cartRequestDto);
        } catch (BusinessLogicException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(cartResponseDto);
    }
}
