package com.roofiahmad.springstoreapp.reviews;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductReviewMapper {
    @Mapping(source = "user.name", target = "userName")
    @Mapping(source = "product.id", target = "productId")
    ProductReviewDto toProductReviewDto(ProductReview review);

    List<ProductReviewDto> toProductReviewDto(List<ProductReview> reviews);
}
