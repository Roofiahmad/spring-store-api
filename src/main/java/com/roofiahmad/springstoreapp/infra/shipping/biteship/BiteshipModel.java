package com.roofiahmad.springstoreapp.infra.shipping.biteship;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

record BiteshipRateRequest(
        @JsonProperty("origin_postal_code") int originPostalCode,
        @JsonProperty("destination_postal_code") int destinationPostalCode,
        @JsonProperty("couriers") String couriers,
        @JsonProperty("items") List<ItemRequest> items
) {}


record BiteshipRateResponse(
        boolean success,
        List<CourierPricing> pricing
) {}

record CourierPricing(
        String courier_name,
        String courier_service_name,
        long price
) {}
