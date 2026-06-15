package com.roofiahmad.springstoreapp.feature.cart.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ProductReviewRequest {
    private Long productId;

    @NotNull(message = "Rating score is mandatory")
    @DecimalMin(value = "1.0", message = "Rating must be at least 1.0 star")
    @DecimalMax(value = "5.0", message = "Rating cannot exceed 5.0 stars")
    private Integer rating;

    @NotBlank(message = "Review comment cannot be empty")
    @Size(max = 1000, message = "Comment must not exceed 1000 characters")
    private String comment;
}
