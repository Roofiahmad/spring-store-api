package com.roofiahmad.springstoreapp.controllers;

import com.roofiahmad.springstoreapp.dtos.CheckoutRequest;
import com.roofiahmad.springstoreapp.dtos.CheckoutResponse;
import com.roofiahmad.springstoreapp.dtos.ErrorDto;
import com.roofiahmad.springstoreapp.exceptions.CartEmptyException;
import com.roofiahmad.springstoreapp.exceptions.CartNotFoundException;
import com.roofiahmad.springstoreapp.security.UserPrincipal;
import com.roofiahmad.springstoreapp.services.CheckoutService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/checkout")
@Tag(name="Checkout")
public class CheckoutController {
    private final CheckoutService checkoutService;

    @PostMapping
    public CheckoutResponse checkout( @AuthenticationPrincipal UserPrincipal principal, @Valid @RequestBody CheckoutRequest request) {
        return checkoutService.checkout(request, principal);
    }

    @ExceptionHandler({CartNotFoundException.class, CartEmptyException.class})
    public ResponseEntity<ErrorDto> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto(ex.getMessage()));
    }
}
