package com.roofiahmad.springstoreapp.feature.product.category;

import com.roofiahmad.springstoreapp.feature.product.category.dto.CategoryDto;
import com.roofiahmad.springstoreapp.feature.product.category.dto.CreateCategoryRequest;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
    CategoryRepository categoryRepository;

    @CacheEvict(value = "category-list", allEntries = true)
    public Category create(CreateCategoryRequest request) {
        var category = new Category(request.getName());
        categoryRepository.save(category);
        return category;
    }

    @Cacheable(value = "category-list")
    public List<CategoryDto> findAll() {
        return categoryRepository.findAllCategoriesAsDto();
    }
}
