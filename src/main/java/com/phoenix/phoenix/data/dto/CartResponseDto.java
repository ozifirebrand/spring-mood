package com.phoenix.phoenix.data.dto;

import com.phoenix.phoenix.data.models.Item;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class CartResponseDto {
    private List<Item> itemList;
    private double totalPrice;
}
