package com.phoenix.phoenix.service.product;

import com.phoenix.phoenix.data.dto.ProductDto;
import com.phoenix.phoenix.data.models.Product;
import com.phoenix.phoenix.web.exceptions.BusinessLogicException;
import com.phoenix.phoenix.web.exceptions.ProductDoesNotExistException;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product findProductById(Long id) throws ProductDoesNotExistException;
    Product createProduct(ProductDto productDto) throws BusinessLogicException;

}
