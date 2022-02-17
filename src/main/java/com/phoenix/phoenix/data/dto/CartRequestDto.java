package com.phoenix.phoenix.data.dto;

import lombok.Data;

@Data
public class CartRequestDto {
    private Long userId;
    private Long productId;
    private int quantity;

}
