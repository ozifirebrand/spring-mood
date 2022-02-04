package com.phoenix.phoenix.service.product;

import com.phoenix.phoenix.data.dto.ProductDto;
import com.phoenix.phoenix.data.models.Product;
import com.phoenix.phoenix.data.repository.ProductRepository;
import com.phoenix.phoenix.web.exceptions.BusinessLogicException;
import com.phoenix.phoenix.web.exceptions.ProductDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product findProductById(Long id) throws ProductDoesNotExistException {
        if ( id == null ){
            throw new IllegalArgumentException("Id cannot be null");
        }
        Optional<Product> optionalProduct = productRepository.findById(id);
        if ( optionalProduct.isPresent()) return optionalProduct.get();

        throw new ProductDoesNotExistException("Product with id: "+id+" does not exist");
    }

    @Override
    public Product createProduct(ProductDto productDto) throws BusinessLogicException {

        //product dto is not null
        if ( productDto == null ) {
            throw new IllegalArgumentException("Argument cannot be null");
        }
        if ( productRepository.findProductByName(productDto.getName()) != null ) throw new BusinessLogicException("Product already exists");

        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        product.setDescription(productDto.getDescription());

        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(ProductDto productDto) {
        Product product = productRepository.findProductByName(productDto.getName());
        product.setImageUrl(productDto.getImageUrl());
        product.setQuantity(productDto.getQuantity());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());

        return product;
    }
}
