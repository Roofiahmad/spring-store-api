package com.roofiahmad.springstoreapp.infra.web.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
