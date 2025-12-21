package com.roofiahmad.springstoreapp.dtos;

import lombok.Data;

@Data
public class CreateProductRequest {
    private String name;
    private String description;
    private Double price;
    private Integer categoryId;
}
