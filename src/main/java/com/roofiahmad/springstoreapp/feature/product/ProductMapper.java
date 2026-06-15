package com.roofiahmad.springstoreapp.feature.product;

import com.roofiahmad.springstoreapp.feature.product.dto.CreateProductRequest;
import com.roofiahmad.springstoreapp.feature.product.dto.ProductDto;
import com.roofiahmad.springstoreapp.feature.product.gallery.ProductGallery;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ProductMapper {
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    ProductDto toDto(Product product);

    @Mapping(target = "gallery", ignore = true)
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    ProductDto toCatalogDto(Product product);

    Product toEntity(CreateProductRequest request);

    @Mapping(target = "gallery", ignore = true)
    void update(CreateProductRequest request, @MappingTarget Product product);

    @AfterMapping
    default void linkGalleryToParent(@MappingTarget Product product) {
        if (product.getGallery() != null) {
            for (ProductGallery galleryItem : product.getGallery()) {
                galleryItem.setProduct(product);
            }
        }
    }
}
