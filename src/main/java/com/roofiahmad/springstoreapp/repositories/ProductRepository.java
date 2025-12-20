package com.roofiahmad.springstoreapp.repositories;

import com.roofiahmad.springstoreapp.dtos.ProductSummary;
import com.roofiahmad.springstoreapp.dtos.ProductSummaryDTO;
import com.roofiahmad.springstoreapp.entities.Category;
import com.roofiahmad.springstoreapp.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {
    // string
    List<Product> findByName(String productName);
    List<Product> findByNameLike(String productName);
    List<Product> findByNameNotLike(String productName);
    List<Product> findByNameContaining(String productName);
    List<Product> findByNameStartingWith(String productName);
    List<Product> findByNameEndingWith(String productName);
    List<Product> findByNameEndingWithIgnoreCase(String productName);

    // numbers
    List<Product> findByPrice(BigDecimal price);
    List<Product> findByPriceGreaterThan(BigDecimal price);
    List<Product> findByPriceLessThan(BigDecimal price);
    List<Product> findByPriceBetween(BigDecimal price1, BigDecimal price2);
    List<Product> findByPriceGreaterThanEqual(BigDecimal price);
    List<Product> findByPriceLessThanEqual(BigDecimal price);

    // null
    List<Product> findByPriceNull(BigDecimal price);

    List<Product> findByCategory(Category category);

    // multiple conditions
    List<Product> findByPriceNullAndNameNull(BigDecimal price, String name);
    List<Product> findByNameOrderByPriceDesc(String name);
    List<Product> findByNameLikeOrderByPriceDesc(String name);
    List<Product> findFirst10ByCategoryOrderByPriceDesc(Category category);

    // using query annotation
    @Query(value = "SELECT * FROM products p WHERE p.price BETWEEN :min AND :max ORDER BY p.name", nativeQuery = true)
    List<Product> findProducts(@Param("min") BigDecimal min, @Param("max") BigDecimal max);

    // using projection to just get specific column
    // projection with interface
    List<ProductSummary> findProductsByCategory(@Param("category") Category category);

    // using below if you have additional logic in that class, else using interface like above
    // projection with dto class
    @Query("select  new com.roofiahmad.springstoreapp.dtos.ProductSummaryDTO(p.id, p.name) from Product p where p.category = :category")
    List<ProductSummaryDTO> findProductsByCategoryDto(@Param("category") Category category);
}
