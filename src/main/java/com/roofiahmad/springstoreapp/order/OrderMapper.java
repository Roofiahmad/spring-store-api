package com.roofiahmad.springstoreapp.order;

import com.roofiahmad.springstoreapp.order.dto.OrderDto;
import com.roofiahmad.springstoreapp.order.dto.OrderItemDto;
import com.roofiahmad.springstoreapp.order.dto.OrderStatusHistoryDto;
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
