package com.roofiahmad.springstoreapp.feature.order.exception;

public class CartEmptyException extends RuntimeException {
    public CartEmptyException() {
        super("Cart is empty");
    }
}
