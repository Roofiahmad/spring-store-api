package com.roofiahmad.springstoreapp.mappers;

import com.roofiahmad.springstoreapp.dtos.OrderDto;
import com.roofiahmad.springstoreapp.entities.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toDto(Order order);
}
