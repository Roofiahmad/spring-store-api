package com.roofiahmad.springstoreapp.products;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/category")
@Tag(name = "Category")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    @PostMapping()
    @CacheEvict(value = "category-list", allEntries = true)
    public ResponseEntity<Category> createCategory(@Valid @RequestBody CreateCategoryRequest request, UriComponentsBuilder uriBuilder
    ) {
        var category = new Category(request.getName());
        categoryRepository.save(category);
        var uri = uriBuilder.path("/category/{id}").buildAndExpand(category.getId()).toUri();
        return ResponseEntity.created(uri).body(category);
    }

    @GetMapping()
    @Cacheable(value = "category-list")
    public ResponseEntity<List<CategoryDto>> getCategories() {
        List<CategoryDto> categories = categoryRepository.findAllCategoriesAsDto();
        return ResponseEntity.ok(categories);
    }
}
