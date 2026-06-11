package com.roofiahmad.springstoreapp.product;

import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @EntityGraph(attributePaths = {"category"})
    @Query("""
    SELECT p FROM Product p 
    WHERE (:categoryId IS NULL OR p.category.id = :categoryId)
      AND (:badge IS NULL OR p.badge = :badge)
      AND (:searchQuery IS NULL OR p.name LIKE CONCAT('%', :searchQuery, '%'))
""")
    Page<Product> findCatalogProducts(
            @Param("searchQuery")  String searchQuery,
            @Param("categoryId") Short categoryId,
            @Param("badge") String badge,
            Pageable pageable
    );

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Product p WHERE p.id IN :ids ORDER BY p.id ASC")
    List<Product> findAllByIdsWithLock(@Param("ids") List<Long> ids);


    long countByStockGreaterThan(int stock);

    @Query("SELECT SUM(p.stock) FROM Product p")
    Long getTotalStock();

    @Query("SELECT p FROM Product p WHERE p.stock = 0")
    List<Product> findOutOfStockProducts();


    @EntityGraph(attributePaths = {"category"})
    @Query("""
    SELECT p FROM Product p 
    WHERE (:categoryId IS NULL OR p.category.id = :categoryId)
     """)
    Page<Product> findByCategory(@Param("categoryId") Short categoryId, Pageable pageable);

}