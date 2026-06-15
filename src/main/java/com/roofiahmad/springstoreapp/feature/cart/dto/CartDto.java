package com.roofiahmad.springstoreapp.feature.cart.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class CartDto {
    private UUID id;
    private Set<CartItemDto> items = new HashSet<>();
    private BigDecimal vatAmount = BigDecimal.ZERO;
    private BigDecimal shippingFee = BigDecimal.ZERO;
    private BigDecimal subTotal = BigDecimal.ZERO;
    private BigDecimal totalPrice = BigDecimal.ZERO;
}
