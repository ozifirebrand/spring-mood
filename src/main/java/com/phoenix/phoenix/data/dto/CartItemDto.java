package com.phoenix.phoenix.data.dto;

import lombok.Data;

@Data
public class CartItemDto {
    private Long productId;
    private Long userId;
    private int quantity;

}
