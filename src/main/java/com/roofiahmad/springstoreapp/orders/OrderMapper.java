package com.roofiahmad.springstoreapp.orders;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(source = "shippingAddressSnapshot", target = "shippingAddress")
    @Mapping(source = "customer.name", target = "customerName")
    OrderDto toDto(Order order);

    OrderStatusHistoryDto statusHistoryToDto(OrderStatusHistory orderStatusHistory);

    @Mapping(source = "product", target = ".")
    OrderItemDto toDto(OrderItem orderItem);

    List<OrderItemDto> toDto(List<OrderItem> orderItems);

    List<OrderDto> toOrderDto(List<Order> orders);
}
