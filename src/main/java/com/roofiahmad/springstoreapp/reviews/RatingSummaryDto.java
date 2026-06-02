package com.roofiahmad.springstoreapp.reviews;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RatingSummaryDto {

    private Long reviewCount;
    private Double averageRating;

    public RatingSummaryDto(Long reviewCount, Number averageRating) {
        this.reviewCount = reviewCount;
        this.averageRating = averageRating != null ? averageRating.doubleValue() : 0.0;
    }
}
