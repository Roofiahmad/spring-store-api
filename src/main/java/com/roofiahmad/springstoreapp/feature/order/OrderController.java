package com.roofiahmad.springstoreapp.feature.order;

import com.roofiahmad.springstoreapp.infra.web.dto.ErrorDto;
import com.roofiahmad.springstoreapp.feature.order.dto.OrderDto;
import com.roofiahmad.springstoreapp.feature.order.dto.OrderReviewRequest;
import com.roofiahmad.springstoreapp.feature.order.exception.OrderNotFoundException;
import com.roofiahmad.springstoreapp.infra.util.Utils;
import jakarta.validation.Valid;
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

    @PostMapping("/{orderId}/delivered")
    public ResponseEntity<OrderDto> confirmOrderReceived(@PathVariable Long orderId) {
        var user = Utils.getUserPrincipal();

        var orderDto = orderService.confirmOrderReceived(user.getId(), orderId);
        if (orderDto == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }

    @PostMapping("/{orderId}/reviews")
    public ResponseEntity<?> addProductReview(@PathVariable Long orderId, @RequestBody @Valid OrderReviewRequest request) {
        var user = Utils.getUserPrincipal();
        orderService.addProductReview(user.getId(), orderId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorDto> handleOrderNotFoundException(OrderNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto(ex.getMessage()));
    }
}
