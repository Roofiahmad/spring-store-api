package com.roofiahmad.springstoreapp.order.dto;

import com.roofiahmad.springstoreapp.cart.dto.ProductReviewRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class OrderReviewRequest {
    @NotEmpty(message = "You must provide at least one product review")
    @Valid
    private List<ProductReviewRequest> reviews;
}
