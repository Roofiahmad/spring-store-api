package com.roofiahmad.springstoreapp.reviews;

import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {
    List<ProductReview> getProductReviewByOrder_Id(Long orderId, Limit limit);

    List<ProductReview> getProductReviewByProduct_Id(Long productId, Limit limit);

    @Query("""
    SELECT new com.roofiahmad.springstoreapp.reviews.RatingSummaryDto(
        COUNT(p.id), 
        CAST(COALESCE(AVG(p.rating), 0.0) AS double)
    ) 
    FROM ProductReview p 
    WHERE p.product.id = :productId
""")
    RatingSummaryDto findRatingSummary(@Param("productId") Long productId);
}
