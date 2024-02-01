package com.example.marketplace.repositories;

import com.example.marketplace.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Boolean existsByNameIgnoreCase(String name);

    @Query("SELECT c FROM Category c WHERE c.id IN :ids")
    List<Category> findAllByIds(@Param("ids") List<Long> ids);
}
