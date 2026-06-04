package com.roofiahmad.springstoreapp.products;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagedResponseMetadata implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private long totalItems;
    private int totalPages;
    private int currentPage;
    private int pageSize;
}
