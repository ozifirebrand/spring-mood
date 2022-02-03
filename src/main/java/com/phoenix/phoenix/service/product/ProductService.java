package com.phoenix.phoenix.service.product;

import com.phoenix.phoenix.data.models.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product findProductById();
    Product createProduct();

}
