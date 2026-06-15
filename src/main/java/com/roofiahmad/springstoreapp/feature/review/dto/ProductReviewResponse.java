package com.roofiahmad.springstoreapp.feature.review.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
public class ProductReviewResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    List<ProductReviewDto> reviews;
    RatingSummaryDto ratingSummary;
}
