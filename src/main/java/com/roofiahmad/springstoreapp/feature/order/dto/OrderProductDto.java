package com.roofiahmad.springstoreapp.feature.order.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderProductDto {
    Long id;
    String name;
    BigDecimal price;
}