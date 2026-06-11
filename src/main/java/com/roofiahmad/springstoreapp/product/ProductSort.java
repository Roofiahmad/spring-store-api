package com.roofiahmad.springstoreapp.product;

import lombok.Getter;
import org.springframework.data.domain.Sort;

@Getter
public enum ProductSort {
    LATEST("createdAt", Sort.Direction.DESC),
    OLDEST("createdAt", Sort.Direction.ASC),
    PRICE_LOW_TO_HIGH("price", Sort.Direction.ASC),
    PRICE_HIGH_TO_LOW("price", Sort.Direction.DESC),
    HIGHEST_STOCK("stock", Sort.Direction.DESC),
    LOWEST_STOCK("stock", Sort.Direction.ASC);


    private final String databaseField;
    private final Sort.Direction direction;

    ProductSort(String databaseField, Sort.Direction direction) {
        this.databaseField = databaseField;
        this.direction = direction;
    }
}
