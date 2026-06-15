package com.roofiahmad.springstoreapp.feature.cart.dto;

import lombok.Data;

@Data
public class CartProductDto {
  private   String id;
  private  String name;
  private  Float price;
  private String mainImage;

}
