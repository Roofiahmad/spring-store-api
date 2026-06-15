package com.roofiahmad.springstoreapp.feature.product.category.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCategoryRequest {
    @NotBlank(message = "category name must be provided")
    private String name;

}
