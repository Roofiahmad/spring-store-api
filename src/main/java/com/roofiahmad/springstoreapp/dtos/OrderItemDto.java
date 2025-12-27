package com.roofiahmad.springstoreapp.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDto  {
    OrderProductDto product;
    Integer quantity;
    BigDecimal totalPrice;
}