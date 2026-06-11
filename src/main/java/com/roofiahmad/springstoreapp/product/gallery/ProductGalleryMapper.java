package com.roofiahmad.springstoreapp.product.gallery;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductGalleryMapper {
    ProductGalleryDto toDto(ProductGallery entity);
    ProductGallery toEntity(ProductGalleryDto dto);
}



