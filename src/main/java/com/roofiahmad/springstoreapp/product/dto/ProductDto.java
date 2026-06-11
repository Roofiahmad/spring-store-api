package com.roofiahmad.springstoreapp.product.dto;

import com.roofiahmad.springstoreapp.product.gallery.ProductGalleryDto;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
public class ProductDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

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
