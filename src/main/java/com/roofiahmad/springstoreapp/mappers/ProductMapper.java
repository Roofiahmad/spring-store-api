package com.roofiahmad.springstoreapp.mappers;

import com.roofiahmad.springstoreapp.dtos.CreateProductRequest;
import com.roofiahmad.springstoreapp.dtos.ProductDto;
import com.roofiahmad.springstoreapp.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "category.id", target = "categoryId")
    ProductDto toDto(Product product);

    Product toEntity(CreateProductRequest request);

    void update(CreateProductRequest request, @MappingTarget Product product);

}
