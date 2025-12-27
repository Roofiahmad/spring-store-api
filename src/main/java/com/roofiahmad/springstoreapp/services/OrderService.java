package com.roofiahmad.springstoreapp.services;

import com.roofiahmad.springstoreapp.dtos.OrderDto;
import com.roofiahmad.springstoreapp.exceptions.OrderNotFoundException;
import com.roofiahmad.springstoreapp.mappers.OrderMapper;
import com.roofiahmad.springstoreapp.repositories.OrderRepository;
import com.roofiahmad.springstoreapp.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserRepository userRepository;

    public List<OrderDto> getAllOrders(Long userId) {
        var orders = orderRepository.getOrdersByCustomer(userId);
        return orders.stream().map(orderMapper::toDto).toList();
    }

    public OrderDto getOrder(Long customerId, Long orderId) {
        var order = orderRepository.getOneOrderWithItems(orderId).orElseThrow(OrderNotFoundException::new);

        if(!order.isPlacedBy(customerId)) {
            throw new AccessDeniedException("You do not have permission to access this order");
        }

        return orderMapper.toDto(order);
    }
}
