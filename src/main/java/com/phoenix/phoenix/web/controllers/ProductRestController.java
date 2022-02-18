package com.phoenix.phoenix.web.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.phoenix.phoenix.data.dto.ProductDto;
import com.phoenix.phoenix.data.models.Product;
import com.phoenix.phoenix.service.product.ProductService;
import com.phoenix.phoenix.web.exceptions.BusinessLogicException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/product")
public class ProductRestController {

    @Autowired
    ProductService productService;

    @GetMapping()
    public ResponseEntity<?> findAllProducts(){

        List<Product> productList = productService.getAllProducts();

        return ResponseEntity.ok().body(productList);
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<?> createProduct(@ModelAttribute ProductDto productDto){

        try{
            Product savedProduct = productService.createProduct(productDto);
            return ResponseEntity.ok().body(savedProduct);
        }
        catch (BusinessLogicException|IllegalArgumentException exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody JsonPatch patch){
        try{
            Product updatedProduct = productService.updateProduct(id, patch);
            return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
        } catch (BusinessLogicException | JsonPatchException | JsonProcessingException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }

    }
}