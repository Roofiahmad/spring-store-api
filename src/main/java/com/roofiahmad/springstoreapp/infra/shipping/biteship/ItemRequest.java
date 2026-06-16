package com.roofiahmad.springstoreapp.infra.shipping.biteship;

import com.roofiahmad.springstoreapp.feature.cart.CartItem;

public record ItemRequest(
        String name,
        String description,
        long value,
        long length,
        long width,
        long height,
        long weight,
        int quantity


) {

    public ItemRequest(CartItem item) {
        this(
                item.getProduct().getName(),
                item.getProduct().getDescription(),
                item.getProduct().getPrice().longValue(),
                item.getProduct().getLength(),
                item.getProduct().getWidth(),
                item.getProduct().getHeight(),
                item.getProduct().getWeight(),
                item.getQuantity()
        );
    }
}
