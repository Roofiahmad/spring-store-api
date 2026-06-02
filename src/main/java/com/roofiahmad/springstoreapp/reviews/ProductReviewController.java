package com.roofiahmad.springstoreapp.reviews;

import com.roofiahmad.springstoreapp.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ProductReviewController {
    private final ProductReviewService productReviewService;

    @GetMapping("orders/{orderId}/reviews")
    public ResponseEntity<?> getReviewFromOrder(@PathVariable Long orderId) {
        var user = Utils.getUserPrincipal();

        List<ProductReviewDto> reviews = productReviewService.findReviewByOrderId(orderId, user);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("products/{productId}/reviews")
    public ResponseEntity<ProductReviewResponse> getReviewFromProduct(@PathVariable Long productId) {
        ProductReviewResponse reviewResponse = productReviewService.findReviewByProductId(productId);
        return new ResponseEntity<>(reviewResponse, HttpStatus.OK);
    }
}
