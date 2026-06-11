package com.roofiahmad.springstoreapp.review.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class RatingSummaryDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long reviewCount;
    private Double averageRating;

    public RatingSummaryDto(Long reviewCount, Number averageRating) {
        this.reviewCount = reviewCount;
        this.averageRating = averageRating != null ? averageRating.doubleValue() : 0.0;
    }
}
