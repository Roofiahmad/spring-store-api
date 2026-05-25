package com.roofiahmad.springstoreapp.products;

import com.roofiahmad.springstoreapp.products.gallery.ProductGallery;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    ProductDto toDto(Product product);

    Product toEntity(CreateProductRequest request);

    @Mapping(target = "gallery", ignore = true)
    void update(CreateProductRequest request, @MappingTarget Product product);

    @AfterMapping
    default void linkGalleryToParent(CreateProductRequest request, @MappingTarget Product product) {
        if (product.getGallery() != null) {
            for (ProductGallery galleryItem : product.getGallery()) {
                galleryItem.setProduct(product);
            }
        }
    }
}
