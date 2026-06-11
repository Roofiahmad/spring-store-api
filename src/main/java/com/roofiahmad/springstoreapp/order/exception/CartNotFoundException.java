package com.roofiahmad.springstoreapp.order.exception;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException() {
        super("Cart not found");
    }
}
