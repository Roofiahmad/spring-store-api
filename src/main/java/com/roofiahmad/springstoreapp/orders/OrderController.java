package com.roofiahmad.springstoreapp.orders;

import com.roofiahmad.springstoreapp.common.ErrorDto;
import com.roofiahmad.springstoreapp.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public List<OrderDto> getOrders() {
        var user = Utils.getUserPrincipal();
        return orderService.getAllOrders(user.getId());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable Long orderId) {
        var user = Utils.getUserPrincipal();

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
