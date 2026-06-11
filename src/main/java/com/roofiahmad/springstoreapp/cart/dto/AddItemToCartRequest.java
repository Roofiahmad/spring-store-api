package com.roofiahmad.springstoreapp.cart.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddItemToCartRequest {
    @NotNull
    private Long productId;
}
