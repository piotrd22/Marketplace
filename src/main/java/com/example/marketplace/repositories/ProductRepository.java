package com.example.marketplace.repositories;

import com.example.marketplace.dtos.request.ProductFilterDto;
import com.example.marketplace.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAll(Pageable pageable);

    @Query("SELECT p FROM Product p WHERE " +
            "(:#{#filterDto.getCategoryIds()} IS NULL OR EXISTS (SELECT c FROM p.categories c WHERE c IN :#{#filterDto.getCategoryIds()})) " +
            "AND (:#{#filterDto.getName()} IS NULL OR LOWER(p.name) LIKE LOWER(concat('%', :#{#filterDto.getName()}, '%'))) " +
            "AND (:#{#filterDto.getDescription()} IS NULL OR LOWER(p.description) LIKE LOWER(concat('%', :#{#filterDto.getDescription()}, '%'))) " +
            "AND (:#{#filterDto.getMinPrice()} IS NULL OR p.price >= :#{#filterDto.getMinPrice()}) " +
            "AND (:#{#filterDto.getMaxPrice()} IS NULL OR p.price <= :#{#filterDto.getMaxPrice()})")
    Page<Product> searchProducts(@Param("filterDto") ProductFilterDto filterDto, Pageable pageable);
}
