package com.roofiahmad.springstoreapp.feature.product;

import com.roofiahmad.springstoreapp.infra.web.exception.BadRequestException;
import com.roofiahmad.springstoreapp.infra.web.exception.NotFoundException;
import com.roofiahmad.springstoreapp.infra.web.dto.PagedResponse;
import com.roofiahmad.springstoreapp.infra.web.dto.PagedResponseMetadata;
import com.roofiahmad.springstoreapp.feature.product.category.CategoryRepository;
import com.roofiahmad.springstoreapp.feature.product.dto.CreateProductRequest;
import com.roofiahmad.springstoreapp.feature.product.dto.ProductDto;
import com.roofiahmad.springstoreapp.feature.product.gallery.ProductGallery;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@AllArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    @Cacheable(value = "product-lists")
    public PagedResponse<ProductDto> findAllProducts(String searchQuery, Short categoryId, String badge, Pageable pageable) {
        String targetBadge = StringUtils.hasText(badge) ? badge : null;

        Page<Product> productPage = productRepository.findCatalogProducts(searchQuery,categoryId, targetBadge, pageable);

        List<ProductDto> productDtos = productPage.getContent().stream()
                .map(productMapper::toCatalogDto)
                .toList();

        PagedResponseMetadata metadata = PagedResponseMetadata.builder()
                .totalItems(productPage.getTotalElements())
                .totalPages(productPage.getTotalPages())
                .currentPage(productPage.getNumber())
                .pageSize(productPage.getSize())
                .build();

        return new PagedResponse<>(productDtos, metadata);
    }

    @Cacheable(value = "product-details", key = "#id")
    public ProductDto findProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        return productMapper.toDto(product);
    }

    @CacheEvict(value = "product-lists", allEntries = true)
    public ProductDto createProduct(CreateProductRequest request) {
        if(request.getCategoryId() == null) {
            throw new BadRequestException("Category id is required");
        }

        var category = categoryRepository.findById(request.getCategoryId());
        if (category.isEmpty()) {
           throw new NotFoundException("Category not found");
        }

        var productEntity = productMapper.toEntity(request);
        productEntity.setCategory(category.get());
        productEntity = productRepository.save(productEntity);

        return productMapper.toDto(productEntity);
    }

    @Caching(evict = {
            @CacheEvict(value = "product-details", key = "#productId"),
            @CacheEvict(value = "product-lists", allEntries = true)
    })
    @Transactional
    public ProductDto updateProduct(Long productId, CreateProductRequest request) {
        if(productId == null) {
            throw new BadRequestException("Product id is required");
        }

        var product = productRepository.findById(productId).orElseThrow(()-> new NotFoundException("Product not found"));

        productMapper.update(request, product);
        if(request.getCategoryId() != null) {
            categoryRepository.findById(request.getCategoryId()).ifPresent(product::setCategory);
        }

        if(request.getGallery() != null) {
            product.getGallery().clear();

            request.getGallery().forEach(g -> {
                var newGallery = new ProductGallery();
                newGallery.setUrl(g.getUrl());
                newGallery.setProduct(product);
                product.getGallery().add(newGallery);
            });
        }

        productRepository.saveAndFlush(product);
        return productMapper.toDto(product);
    }

    @Caching(evict = {
            @CacheEvict(value = "product-details", key = "#productId"),
            @CacheEvict(value = "product-lists", allEntries = true)
    })
    @Transactional
    public void deleteProduct(Long productId) {
        if(productId == null) {
            throw new BadRequestException("Product id is required");
        }

        var product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product not found"));

        productRepository.delete(product);
    }
}
