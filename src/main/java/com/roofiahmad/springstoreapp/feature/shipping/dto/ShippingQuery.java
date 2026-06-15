package com.roofiahmad.springstoreapp.feature.shipping.dto;

import com.roofiahmad.springstoreapp.infra.shipping.biteship.ItemRequest;

import java.util.List;

public record ShippingQuery(
        int originPostalCode,
        int destinationPostalCode,
        List<ItemRequest> items,
        int totalWeightGrams
) {}
