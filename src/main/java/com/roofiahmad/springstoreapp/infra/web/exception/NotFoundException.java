package com.roofiahmad.springstoreapp.infra.web.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
