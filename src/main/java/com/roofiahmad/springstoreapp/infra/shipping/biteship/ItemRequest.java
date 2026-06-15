package com.roofiahmad.springstoreapp.infra.shipping.biteship;

public record ItemRequest(
        String name,
        String description,
        long value,
        long length,
        long width,
        long height,
        long weight,
        int quantity


) {}
