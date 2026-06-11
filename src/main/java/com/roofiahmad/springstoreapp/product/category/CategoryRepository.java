package com.roofiahmad.springstoreapp.product.category;

import com.roofiahmad.springstoreapp.product.category.dto.CategoryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Short> {
    @Query("select new com.roofiahmad.springstoreapp.product.category.dto.CategoryDto(c.id, c.name) from Category c")
    List<CategoryDto> findAllCategoriesAsDto();
}
