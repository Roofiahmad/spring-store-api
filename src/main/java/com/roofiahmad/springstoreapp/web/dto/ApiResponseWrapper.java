package com.roofiahmad.springstoreapp.web.dto;

import java.time.LocalDateTime;

public class ApiResponseWrapper<T> {
    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public ApiResponseWrapper(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    public static <T> ApiResponseWrapper<T> success(T data, String message) {
        return new ApiResponseWrapper<>(true, message, data);
    }

    public static <T> ApiResponseWrapper<T> success(T data) {
        return success(data, "Operation completed successfully.");
    }

    public static <T> ApiResponseWrapper<T> error(String errorMessage) {
        return new ApiResponseWrapper<>(false, errorMessage, null);
    }

    // Getters
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public T getData() { return data; }
    public LocalDateTime getTimestamp() { return timestamp; }
}