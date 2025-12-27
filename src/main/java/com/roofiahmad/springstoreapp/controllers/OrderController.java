package com.roofiahmad.springstoreapp.controllers;

import com.roofiahmad.springstoreapp.dtos.ErrorDto;
import com.roofiahmad.springstoreapp.dtos.OrderDto;
import com.roofiahmad.springstoreapp.exceptions.OrderNotFoundException;
import com.roofiahmad.springstoreapp.security.UserPrincipal;
import com.roofiahmad.springstoreapp.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public List<OrderDto> getOrders() {
        var user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return orderService.getAllOrders(user.getId());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable Long orderId) {
        var user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        var orderDto = orderService.getOrder(user.getId(), orderId);
        if (orderDto == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorDto> handleOrderNotFoundException(OrderNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto(ex.getMessage()));
    }
}
