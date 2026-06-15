package com.roofiahmad.springstoreapp.feature.order.dto;

import com.roofiahmad.springstoreapp.feature.address.dto.AddressDto;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDto {
    private Long id;
    private String status;
    private BigDecimal totalPrice;
    private BigDecimal shippingFee;
    private BigDecimal vatAmount;
    private BigDecimal subTotal;
    private LocalDateTime createdAt;
    private List<OrderItemDto> items;
    private AddressDto shippingAddress;
    private List<OrderStatusHistoryDto> statusHistory;
    private String customerPhoneNumber;
    private String customerEmail;
    private String customerName;
}
