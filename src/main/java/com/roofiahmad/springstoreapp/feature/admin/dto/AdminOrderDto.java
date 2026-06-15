package com.roofiahmad.springstoreapp.feature.admin.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AdminOrderDto {
    private Long id;
    private String status;
    private BigDecimal totalPrice;
    private BigDecimal shippingFee;
    private BigDecimal vatAmount;
    private BigDecimal subTotal;
    private LocalDateTime createdAt;
    private String customerPhoneNumber;
}
