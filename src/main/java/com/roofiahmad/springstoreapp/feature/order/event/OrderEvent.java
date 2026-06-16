package com.roofiahmad.springstoreapp.feature.order.event;

import com.roofiahmad.springstoreapp.feature.order.Order;
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
) {
    public OrderEvent(Order order, List<OrderItemEvent>  items) {
        this(
                order.getCustomer().getName(),
                order.getCustomerEmail(),
                order.getId().toString(),
                items,
                order.getSubTotal().doubleValue(),
                order.getShippingFee().doubleValue(),
                order.getVatAmount().doubleValue(),
                order.getTotalPrice().doubleValue()
        );
    }
}
