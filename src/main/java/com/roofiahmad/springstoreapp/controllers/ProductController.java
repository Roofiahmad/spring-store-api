package com.roofiahmad.springstoreapp.controllers;

import com.roofiahmad.springstoreapp.dtos.CreateProductRequest;
import com.roofiahmad.springstoreapp.dtos.ProductDto;
import com.roofiahmad.springstoreapp.entities.Product;
import com.roofiahmad.springstoreapp.mappers.ProductMapper;
import com.roofiahmad.springstoreapp.repositories.CategoryRepository;
import com.roofiahmad.springstoreapp.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    @GetMapping
    public List<ProductDto> getAllProducts(
            @RequestParam(required = false, defaultValue = "", name = "categoryId") Short categoryId
    ) {
        List<Product> products = (categoryId == null)
                ? productRepository.findAllProduct()
                : productRepository.findByCategoryId(categoryId);

        return products.stream()
                .map(productMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id
    ) {
        return productRepository.findById(id)
                .map(productMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<ProductDto> createProduct(@RequestBody CreateProductRequest request, UriComponentsBuilder uriBuilder
    ) {
        if(request.getCategoryId() == null) {
            return ResponseEntity.badRequest().build();
        }

        var category = categoryRepository.findById(request.getCategoryId());

        if (category.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var productEntity = productMapper.toEntity(request);
        productEntity.setCategory(category.get());
        productEntity = productRepository.save(productEntity);

        var productDto = productMapper.toDto(productEntity);
        var uri = uriBuilder.path("/products/{id}").buildAndExpand(productDto.getId()).toUri();
        return ResponseEntity.created(uri).body(productDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id,@RequestBody CreateProductRequest request
    ) {
        if(id == null) {
            return ResponseEntity.badRequest().build();
        }

       var product = productRepository.findById(id).orElse(null);
        if(product == null) {
            return ResponseEntity.notFound().build();
        }

        productMapper.update(request, product);
        if(request.getCategoryId() != null) {
            categoryRepository.findById(request.getCategoryId()).ifPresent(product::setCategory);
        }
        productRepository.save(product);

        return ResponseEntity.ok(productMapper.toDto(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id
    ) {
        if(id == null) {
            return ResponseEntity.badRequest().build();
        }

        var product = productRepository.findById(id).orElse(null);
        if(product == null) {
            return ResponseEntity.notFound().build();
        }

        productRepository.delete(product);


        return ResponseEntity.noContent().build();
    }
}
