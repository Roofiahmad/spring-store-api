package com.roofiahmad.springstoreapp.admin;

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
