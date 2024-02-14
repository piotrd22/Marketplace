package com.example.marketplace.repositories;

import com.example.marketplace.models.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartProductRepository extends JpaRepository<CartProduct, Long> { }
