package com.roofiahmad.springstoreapp.feature.admin.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AdminStatisticDto {
    Long listedProducts;
    Long totalStockVolume;
    List<AdminProductDto> productShortages;
    List<AdminOrderDto> orderPendingFulfillment;
}
