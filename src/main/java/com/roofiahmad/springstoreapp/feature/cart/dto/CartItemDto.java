package com.roofiahmad.springstoreapp.feature.cart.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemDto {
   private String id;
   private String name;
   private Float price;
   private String mainImage;
   private Integer quantity;
   private BigDecimal totalPrice;
}
