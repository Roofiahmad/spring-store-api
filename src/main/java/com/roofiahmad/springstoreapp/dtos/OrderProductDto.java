package com.roofiahmad.springstoreapp.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderProductDto {
    Long id;
    String name;
    BigDecimal price;
}