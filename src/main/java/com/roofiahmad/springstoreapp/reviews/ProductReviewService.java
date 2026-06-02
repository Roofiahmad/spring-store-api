package com.roofiahmad.springstoreapp.reviews;

import com.roofiahmad.springstoreapp.auth.UserPrincipal;
import com.roofiahmad.springstoreapp.orders.OrderNotFoundException;
import com.roofiahmad.springstoreapp.orders.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Limit;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductReviewService {
    private final ProductReviewRepository productReviewRepository;
    private final ProductReviewMapper productReviewMapper;
    private OrderRepository orderRepository;

    List<ProductReviewDto> findReviewByOrderId(Long orderId, UserPrincipal user) {
        var order = orderRepository.getOneOrderById(orderId)
                .orElseThrow(OrderNotFoundException::new);

        if (!order.isPlacedBy(user.getId())) {
            throw new AccessDeniedException("You do not have permission to access this order");
        }

       List<ProductReview> reviews = productReviewRepository.getProductReviewByOrder_Id(orderId, Limit.of(5));
        return productReviewMapper.toProductReviewDto(reviews);
    }

    ProductReviewResponse findReviewByProductId(Long productId) {
        List<ProductReview> reviews = productReviewRepository.getProductReviewByProduct_Id(productId, Limit.of(5));
       RatingSummaryDto ratingSummary = productReviewRepository.findRatingSummary(productId);
       ProductReviewResponse reviewResponse = new ProductReviewResponse();
       reviewResponse.setRatingSummary(ratingSummary);
       reviewResponse.setReviews(productReviewMapper.toProductReviewDto(reviews));
        return reviewResponse;
    }
}
