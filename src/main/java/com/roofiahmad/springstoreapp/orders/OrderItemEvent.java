package com.roofiahmad.springstoreapp.orders;

import java.math.BigDecimal;

public record OrderItemEvent(
        String name,
        int quantity,
        BigDecimal price
) {}
