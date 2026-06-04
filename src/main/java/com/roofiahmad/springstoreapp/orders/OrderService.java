package com.roofiahmad.springstoreapp.orders;

import com.roofiahmad.springstoreapp.payments.PaymentResult;
import com.roofiahmad.springstoreapp.payments.PaymentStatus;
import com.roofiahmad.springstoreapp.products.Product;
import com.roofiahmad.springstoreapp.reviews.ProductReview;
import com.roofiahmad.springstoreapp.reviews.ProductReviewRepository;
import com.roofiahmad.springstoreapp.services.OrderEventPublisher;
import com.roofiahmad.springstoreapp.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
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
            List<OrderItemEvent> itemPayloads = order.getItems().stream()
                    .map(item -> new OrderItemEvent(
                            item.getProduct().getName(),
                            item.getQuantity(),
                            item.getUnitPrice()
                    ))
                    .toList();

            OrderEvent orderEvent = OrderEvent.builder().customerName(order.getCustomer().getName())
                    .customerEmail(order.getCustomerEmail())
                    .orderNumber(order.getId().toString())
                    .items(itemPayloads)
                    .subtotal(order.getSubTotal().doubleValue())
                    .shippingFee(order.getShippingFee().doubleValue())
                    .vatAmount(order.getVatAmount().doubleValue())
                    .totalAmount(order.getTotalPrice().doubleValue()).build();

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

        var order = orderRepository.getOneOrderWithItems(orderId)
                .orElseThrow(OrderNotFoundException::new);

        if (!order.isPlacedBy(customerId)) {
            throw new AccessDeniedException("You do not have permission to access this order");
        }

        Map<Long, Product> purchasedProductsMap = order.getItems().stream()
                .map(OrderItem::getProduct)
                .collect(Collectors.toMap(
                        Product::getId,
                        product -> product,
                        (existing, replacement) -> existing
                ));

        boolean allProductsBelongToOrder = request.getReviews().stream()
                .allMatch(review -> purchasedProductsMap.containsKey(review.getProductId()));

        if (!allProductsBelongToOrder) {
            throw new AccessDeniedException("Some products were not purchased in this order");
        }

        request.getReviews().forEach(review -> {
            ProductReview productReview = new ProductReview();
            Product product = purchasedProductsMap.get(review.getProductId());

            productReview.setComment(review.getComment());
            productReview.setRating(review.getRating());
            productReview.setUser(order.getCustomer());
            productReview.setProduct(product);
            productReview.setVerifiedPurchase(true);
            productReview.setOrder(order);

            productReviewRepository.save(productReview);
        });
    }

}
