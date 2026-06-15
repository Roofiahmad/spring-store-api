package com.roofiahmad.springstoreapp.feature.admin.dto;

import lombok.Data;

@Data
public class AdminProductDto {
    private Long id;
    private String name;
    private String description;
    private String mainImage;
    private Double price;
    private Integer stock;
    private String badge;
    private Byte categoryId;
    private String categoryName;
}
