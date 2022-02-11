package com.phoenix.phoenix.data.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductDto {

    private String name;
    private String description;
    private double price;
    private int quantity;
    private MultipartFile image;

}
