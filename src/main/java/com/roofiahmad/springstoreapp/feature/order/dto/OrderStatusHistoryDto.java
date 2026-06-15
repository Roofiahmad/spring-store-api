package com.roofiahmad.springstoreapp.feature.order.dto;

import com.roofiahmad.springstoreapp.feature.payment.PaymentStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderStatusHistoryDto {

    private PaymentStatus status;
    private String notes;
    private LocalDateTime createdAt = LocalDateTime.now();
}
