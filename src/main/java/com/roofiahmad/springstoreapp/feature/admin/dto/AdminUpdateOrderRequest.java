package com.roofiahmad.springstoreapp.feature.admin.dto;

import com.roofiahmad.springstoreapp.feature.payment.PaymentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AdminUpdateOrderRequest {
    @NotNull(message = "Payment status is mandatory")
    private PaymentStatus status;
}
