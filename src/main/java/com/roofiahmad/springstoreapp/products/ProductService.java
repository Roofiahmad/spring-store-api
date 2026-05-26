package com.roofiahmad.springstoreapp.products;

import com.roofiahmad.springstoreapp.common.BadRequestException;
import com.roofiahmad.springstoreapp.common.NotFoundException;
import com.roofiahmad.springstoreapp.products.gallery.ProductGallery;
import lombok.AllArgsConstructor;
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

    public PagedResponse<ProductDto>findAllProducts(Short categoryId, String badge, Pageable pageable) {
        String targetBadge = StringUtils.hasText(badge) ? badge : null;

        Page<Product> productPage = productRepository.findCatalogProducts(categoryId, targetBadge, pageable);

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

    public ProductDto findProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        return productMapper.toDto(product);
    }

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

    public void deleteProduct(Long productId) {
        if(productId == null) {
            throw new BadRequestException("Product id is required");
        }

        var product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product not found"));

        productRepository.delete(product);
    }
}
