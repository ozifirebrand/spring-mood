package com.phoenix.phoenix.data.repository;

import com.phoenix.phoenix.data.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findProductByName(String name);
}
