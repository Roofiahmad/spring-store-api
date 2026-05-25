package com.roofiahmad.springstoreapp.products;

import com.roofiahmad.springstoreapp.products.gallery.ProductGalleryDto;
import lombok.Data;

import java.util.List;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private String mainImage;
    private Double price;
    private Integer stock;
    private String badge;
    private Byte categoryId;
    private String categoryName;
    private List<ProductGalleryDto> gallery;
}
