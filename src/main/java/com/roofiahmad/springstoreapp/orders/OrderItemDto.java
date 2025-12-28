package com.roofiahmad.springstoreapp.orders;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDto  {
    OrderProductDto product;
    Integer quantity;
    BigDecimal totalPrice;
}