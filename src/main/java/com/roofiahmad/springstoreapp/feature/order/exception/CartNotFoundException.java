package com.roofiahmad.springstoreapp.feature.order.exception;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException() {
        super("Cart not found");
    }
}
