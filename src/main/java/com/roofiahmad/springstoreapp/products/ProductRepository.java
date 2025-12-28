package com.roofiahmad.springstoreapp.products;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @EntityGraph(attributePaths = "category")
    public List<Product> findByCategoryId(Short categoryId);

    @EntityGraph(attributePaths = "category")
    @Query("select p from Product p")
    public List<Product> findAllProduct();

    @EntityGraph(attributePaths = "category")
    @Query("select p from Product p where p.category.id = :categoryId")
    public List<Product>  getAllByCategoryId(@Param("categoryId") Long categoryId );
}
