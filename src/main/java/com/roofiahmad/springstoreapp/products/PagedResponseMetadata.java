package com.roofiahmad.springstoreapp.products;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagedResponseMetadata {
    private long totalItems;
    private int totalPages;
    private int currentPage;
    private int pageSize;
}
