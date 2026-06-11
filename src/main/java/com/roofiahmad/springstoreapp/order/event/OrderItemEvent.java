package com.roofiahmad.springstoreapp.order.event;

import java.math.BigDecimal;

public record OrderItemEvent(
        String name,
        int quantity,
        BigDecimal price
) {}
