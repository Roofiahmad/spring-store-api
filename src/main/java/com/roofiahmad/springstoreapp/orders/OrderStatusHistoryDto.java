package com.roofiahmad.springstoreapp.orders;

import com.roofiahmad.springstoreapp.payments.PaymentStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderStatusHistoryDto {

    private PaymentStatus status;
    private String notes;
    private LocalDateTime createdAt = LocalDateTime.now();
}
