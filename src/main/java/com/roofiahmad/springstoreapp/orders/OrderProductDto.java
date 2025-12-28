package com.roofiahmad.springstoreapp.orders;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderProductDto {
    Long id;
    String name;
    BigDecimal price;
}