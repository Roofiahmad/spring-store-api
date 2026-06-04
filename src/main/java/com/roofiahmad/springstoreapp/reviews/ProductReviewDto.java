package com.roofiahmad.springstoreapp.reviews;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
public class ProductReviewDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long productId;
    private String userName;
    private Integer rating;
    private String comment;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
