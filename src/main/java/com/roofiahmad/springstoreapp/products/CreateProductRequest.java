package com.roofiahmad.springstoreapp.products;

import com.roofiahmad.springstoreapp.products.gallery.ProductGalleryDto;
import lombok.Data;

import java.util.List;

@Data
public class CreateProductRequest {
    private String name;
    private String description;
    private String mainImage;;
    private Double price;
    private String badge;
    private Integer stock;
    private Short categoryId;
    private List<ProductGalleryDto> gallery;
}
