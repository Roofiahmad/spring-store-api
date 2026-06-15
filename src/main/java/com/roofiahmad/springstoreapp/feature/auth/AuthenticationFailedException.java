package com.roofiahmad.springstoreapp.feature.auth;

public class AuthenticationFailedException extends RuntimeException {
    public AuthenticationFailedException(String message) {
        super(message);
    }
}
