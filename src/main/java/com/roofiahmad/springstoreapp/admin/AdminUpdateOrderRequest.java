package com.roofiahmad.springstoreapp.admin;

import com.roofiahmad.springstoreapp.payments.PaymentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AdminUpdateOrderRequest {
    @NotNull(message = "Payment status is mandatory")
    private PaymentStatus status;
}
