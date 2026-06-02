package com.roofiahmad.springstoreapp.orders;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDto  {
    private Long id;
    private String name;
    private BigDecimal unitPrice;
    private Integer quantity;
    private String mainImage;
    private BigDecimal totalPrice;
}