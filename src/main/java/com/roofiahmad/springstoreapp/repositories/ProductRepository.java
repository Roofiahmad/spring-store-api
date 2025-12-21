package com.roofiahmad.springstoreapp.repositories;

import com.roofiahmad.springstoreapp.entities.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @EntityGraph(attributePaths = "category")
    public List<Product> findByCategoryId(Short categoryId);

    @EntityGraph(attributePaths = "category")
    @Query("select p from Product p")
    public List<Product> findAllProduct();
}
