package com.roofiahmad.springstoreapp.common.exception;

import com.roofiahmad.springstoreapp.auth.AuthenticationFailedException;
import com.roofiahmad.springstoreapp.common.dto.ApiResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler  {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseWrapper<Map<String, String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        ApiResponseWrapper<Map<String, String>> errorResponse = new ApiResponseWrapper<>(
                false,
                "Validation validation faults detected.",
                errors
        );
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponseWrapper<Void>> handleUnreadableMessage() {
        return ResponseEntity.badRequest().body(ApiResponseWrapper.error("Invalid request body payload format."));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponseWrapper<Void>> handleAccessDenied(AccessDeniedException ex) {
        log.warn("⚠️ Access denied: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponseWrapper.error(ex.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponseWrapper<Void>> handleNotFound(NotFoundException ex) {
        log.warn("⚠️ Resource missing warning: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponseWrapper.error(ex.getMessage()));
    }

    @ExceptionHandler({BadRequestException.class, IllegalArgumentException.class})
    public ResponseEntity<ApiResponseWrapper<Void>> handleBadRequest(RuntimeException ex) {
        log.warn("⚠️ Bad request: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(ApiResponseWrapper.error(ex.getMessage()));
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<ApiResponseWrapper<Void>> handleAuthFailed(AuthenticationFailedException ex) {
        log.warn("⚠️ Authentication failed: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponseWrapper.error(ex.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponseWrapper<Void>> handleBadCredentials(org.springframework.security.authentication.BadCredentialsException ex) {
        log.warn("⚠️ Bad credentials error: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponseWrapper.error(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseWrapper<Void>> handleGeneralCrash(Exception ex) {
        log.error("❌ CRITICAL SERVER ERROR OCCURRED: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ApiResponseWrapper.error("An internal processing error occurred: " + ex.getMessage()));
    }
}