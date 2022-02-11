package com.phoenix.phoenix.data.repository;

import com.phoenix.phoenix.data.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
