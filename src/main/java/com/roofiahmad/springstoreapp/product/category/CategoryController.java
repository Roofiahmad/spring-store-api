package com.roofiahmad.springstoreapp.product.category;

import com.roofiahmad.springstoreapp.product.category.dto.CategoryDto;
import com.roofiahmad.springstoreapp.product.category.dto.CreateCategoryRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/category")
@Tag(name = "Category")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping()
    public ResponseEntity<Category> createCategory(@Valid @RequestBody CreateCategoryRequest request, UriComponentsBuilder uriBuilder
    ) {
        Category category = categoryService.create(request);
        var uri = uriBuilder.path("/category/{id}").buildAndExpand(category.getId()).toUri();
        return ResponseEntity.created(uri).body(category);
    }

    @GetMapping()
    public ResponseEntity<List<CategoryDto>> getCategories() {
        List<CategoryDto> categories = categoryService.findAll();
        return ResponseEntity.ok(categories);
    }
}
