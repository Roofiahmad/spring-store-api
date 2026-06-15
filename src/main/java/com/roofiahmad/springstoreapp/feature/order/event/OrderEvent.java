package com.roofiahmad.springstoreapp.feature.order.event;

import lombok.Builder;

import java.util.List;

@Builder
public record OrderEvent(
        String customerName,
        String customerEmail,
        String orderNumber,
        List<OrderItemEvent> items,
        double subtotal,
        double shippingFee,
        double vatAmount,
        double totalAmount
) {}
