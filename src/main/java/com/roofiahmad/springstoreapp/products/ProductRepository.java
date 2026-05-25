package com.roofiahmad.springstoreapp.products;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @EntityGraph(attributePaths = {"category", "gallery"})
    public List<Product> findByCategoryId(Short categoryId);

    @EntityGraph(attributePaths = {"category", "gallery"})
    @Query("select p from Product p")
    public List<Product> findAllProduct();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Product p WHERE p.id IN :ids ORDER BY p.id ASC")
    List<Product> findAllByIdsWithLock(@Param("ids") List<Long> ids);
}
