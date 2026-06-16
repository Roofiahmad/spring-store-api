package com.roofiahmad.springstoreapp.feature.order;

import com.roofiahmad.springstoreapp.feature.order.dto.OrderDto;
import com.roofiahmad.springstoreapp.feature.order.dto.OrderReviewRequest;
import com.roofiahmad.springstoreapp.feature.order.event.OrderEvent;
import com.roofiahmad.springstoreapp.feature.order.event.OrderEventPublisher;
import com.roofiahmad.springstoreapp.feature.order.event.OrderItemEvent;
import com.roofiahmad.springstoreapp.feature.order.exception.OrderNotFoundException;
import com.roofiahmad.springstoreapp.feature.payment.PaymentResult;
import com.roofiahmad.springstoreapp.feature.payment.PaymentStatus;
import com.roofiahmad.springstoreapp.feature.product.Product;
import com.roofiahmad.springstoreapp.feature.review.ProductReview;
import com.roofiahmad.springstoreapp.feature.review.ProductReviewRepository;
import com.roofiahmad.springstoreapp.infra.util.Utils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ProductReviewRepository productReviewRepository;
    private final OrderEventPublisher orderEventPublisher;

    public List<OrderDto> getAllOrders(Long userId) {
        var orders = orderRepository.getOrdersByCustomer(userId, PageRequest.of(0, 4));
        return orders.stream().map(orderMapper::toDto).toList();
    }

    public OrderDto getOrder(Long customerId, Long orderId) {
        var order = orderRepository.getOneOrderWithItems(orderId).orElseThrow(OrderNotFoundException::new);

        if (!order.isPlacedBy(customerId)) {
            throw new AccessDeniedException("You do not have permission to access this order");
        }

        return orderMapper.toDto(order);
    }

    @Transactional
    public void updatePaymentStatus(PaymentResult paymentResult) {
        var order = orderRepository.findById(paymentResult.getOrderId()).orElseThrow(OrderNotFoundException::new);
        order.insertStatusHistory(paymentResult.getPaymentStatus(), "");
        orderRepository.save(order);

        if (paymentResult.getPaymentStatus() == PaymentStatus.PAID) {
            List<OrderItemEvent> itemPayloads = order.toOrderItemsEvent();
            OrderEvent orderEvent = new OrderEvent(order, itemPayloads);
            orderEventPublisher.publishOrderPaidEvent(orderEvent);
        }

    }


    public OrderDto confirmOrderReceived(Long customerId, Long orderId) {
        var order = orderRepository.getOneOrderWithItems(orderId).orElseThrow(OrderNotFoundException::new);

        if (!order.isPlacedBy(customerId)) {
            throw new AccessDeniedException("You do not have permission to access this order");
        }

        order.insertStatusHistory(PaymentStatus.DELIVERED, "Order receiver by " + Utils.getUserPrincipal().getName());
        orderRepository.save(order);

        return orderMapper.toDto(order);
    }

    @Transactional
    public void addProductReview(Long customerId, Long orderId, OrderReviewRequest request) {
        Order order = orderRepository.getOneOrderWithItems(orderId)
                .orElseThrow(OrderNotFoundException::new);

        if (!order.isPlacedBy(customerId)) {
            throw new AccessDeniedException("You do not have permission to access this order");
        }

        Set<Long> purchasedProductIds = order.getItems().stream()
                .map(item -> item.getProduct().getId())
                .collect(Collectors.toSet());

        boolean allProductsBelongToOrder = request.getReviews().stream()
                .allMatch(review -> purchasedProductIds.contains(review.getProductId()));

        if (!allProductsBelongToOrder) {
            throw new AccessDeniedException("Some products were not purchased in this order");
        }

        request.getReviews().forEach(review -> {
            Product product = order.getItems().stream()
                    .map(OrderItem::getProduct)
                    .filter(p -> p.getId().equals(review.getProductId()))
                    .findFirst()
                    .orElseThrow();

            ProductReview productReview = new ProductReview(review, order, product);
            productReviewRepository.save(productReview);
        });
    }

}
