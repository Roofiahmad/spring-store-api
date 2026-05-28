package com.roofiahmad.springstoreapp.orders;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(source = "shippingAddressSnapshot", target = "shippingAddress")
    OrderDto toDto(Order order);

    @Mapping(source = "product.name", target = "name")
    OrderItemDto toDto(OrderItem orderItem);

    List<OrderItemDto> toDto(List<OrderItem> orderItems);
}
