package com.phoenix.phoenix.controllers;

import com.phoenix.phoenix.data.dto.ProductDto;
import com.phoenix.phoenix.data.models.Product;
import com.phoenix.phoenix.service.product.ProductService;
import com.phoenix.phoenix.web.exceptions.BusinessLogicException;
import com.phoenix.phoenix.web.exceptions.ProductDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.net.ProtocolException;
import java.net.URI;
import java.util.List;

public class ProductRestController {

    @Autowired
    ProductService productService;

    @GetMapping
    public ResponseEntity<?> findAllProducts(){

        List<Product> productList = productService.getAllProducts();

        return ResponseEntity.ok().body(productList);
    }

    @PostMapping
    public ResponseEntity<?> createProduct(ProductDto productDto){

        try{
            Product savedProduct = productService.createProduct(productDto);
            return ResponseEntity.ok().body(savedProduct);
        }
        catch (BusinessLogicException exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
}