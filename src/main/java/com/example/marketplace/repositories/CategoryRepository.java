package com.example.marketplace.repositories;

import com.example.marketplace.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT COUNT(p) > 0 FROM Product p JOIN p.categories c WHERE c.id = :categoryId")
    boolean existsProductInCategory(@Param("categoryId") Long categoryId);

    Boolean existsByNameIgnoreCase(String name);
}
