package com.roofiahmad.springstoreapp.reviews;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class ProductReviewDto {
    private Long id;
    private Long productId;
    private String userName;
    private Integer rating;
    private String comment;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
