package com.roofiahmad.springstoreapp.feature.shipping.dto;

public record AvailableRate(
        String carrierName,
        String serviceType,
        long costIdr
) {}
