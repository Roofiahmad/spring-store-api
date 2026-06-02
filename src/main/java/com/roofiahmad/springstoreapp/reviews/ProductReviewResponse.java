package com.roofiahmad.springstoreapp.reviews;

import lombok.Data;

import java.util.List;

@Data
public class ProductReviewResponse {
    List<ProductReviewDto> reviews;
    RatingSummaryDto ratingSummary;
}
