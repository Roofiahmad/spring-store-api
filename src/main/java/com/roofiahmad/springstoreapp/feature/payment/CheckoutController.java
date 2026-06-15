package com.roofiahmad.springstoreapp.feature.payment;

import com.roofiahmad.springstoreapp.feature.auth.UserPrincipal;
import com.roofiahmad.springstoreapp.infra.web.dto.ErrorDto;
import com.roofiahmad.springstoreapp.feature.order.exception.CartEmptyException;
import com.roofiahmad.springstoreapp.feature.order.exception.CartNotFoundException;
import com.roofiahmad.springstoreapp.feature.payment.dto.CheckoutRequest;
import com.roofiahmad.springstoreapp.feature.payment.dto.CheckoutResponse;
import com.roofiahmad.springstoreapp.feature.payment.dto.WebhookRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/checkout")
@Tag(name="Checkout")
public class CheckoutController {
    private final CheckoutService checkoutService;

    @PostMapping
    public CheckoutResponse checkout(@AuthenticationPrincipal UserPrincipal principal, @Valid @RequestBody CheckoutRequest request) {
           return checkoutService.checkout(request, principal);
    }

    @PostMapping("/webhook")
    public void handleWebHook(@RequestHeader Map<String, String> headers, @RequestBody String payload ){
        checkoutService.handleWebhookEvent(new WebhookRequest(headers, payload));
    }

    @ExceptionHandler({PaymentException.class})
    public ResponseEntity<ErrorDto> handlePaymentException(PaymentException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ErrorDto("Error creating a checkout session")
        );
    }

    @ExceptionHandler({CartNotFoundException.class, CartEmptyException.class})
    public ResponseEntity<ErrorDto> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto(ex.getMessage()));
    }
}
