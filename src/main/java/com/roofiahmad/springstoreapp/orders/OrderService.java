package com.roofiahmad.springstoreapp.orders;

import com.roofiahmad.springstoreapp.common.EmailService;
import com.roofiahmad.springstoreapp.payments.PaymentResult;
import com.roofiahmad.springstoreapp.payments.PaymentStatus;
import com.roofiahmad.springstoreapp.products.Product;
import com.roofiahmad.springstoreapp.products.ProductRepository;
import com.roofiahmad.springstoreapp.reviews.ProductReview;
import com.roofiahmad.springstoreapp.reviews.ProductReviewRepository;
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
    private final EmailService emailService;
    private final ProductRepository productRepository;
    private final ProductReviewRepository productReviewRepository;

    public List<OrderDto> getAllOrders(Long userId) {
        var orders = orderRepository.getOrdersByCustomer(userId, PageRequest.of(0, 4));
        return orders.stream().map(orderMapper::toDto).toList();
    }

    public OrderDto getOrder(Long customerId, Long orderId) {
        var order = orderRepository.getOneOrderWithItems(orderId).orElseThrow(OrderNotFoundException::new);

        if(!order.isPlacedBy(customerId)) {
            throw new AccessDeniedException("You do not have permission to access this order");
        }

        return orderMapper.toDto(order);
    }

    @Transactional
    public void updatePaymentStatus(PaymentResult paymentResult) {
        var order = orderRepository.findById(paymentResult.getOrderId()).orElseThrow(OrderNotFoundException::new);
        System.out.println(paymentResult.getPaymentStatus() + " payment status");
        order.insertStatusHistory(paymentResult.getPaymentStatus(), "");

//        if(paymentResult.getPaymentStatus() == PaymentStatus.PAID) {
//            // send order confirmation email
//            var customer = order.getCustomer();
//            Map<String, Object> model = new HashMap<>();
//            model.put("customerName", customer.getName());
//            model.put("orderNumber", "REF-" + Year.now().getValue() + "-" + order.getId());
//            model.put("items", order.getItems().stream().map(p -> Map.of(
//                            "name", p.getProduct().getName(),
//                            "quantity", p.getQuantity(),
//                            "price", p.getTotalPrice()
//                    )
//            ).toList());
//            model.put("subtotal", order.getSubTotal());
//            model.put("shippingFee", order.getShippingFee());
//            model.put("vatAmount", order.getVatAmount());
//            model.put("totalAmount", order.getTotalPrice());
//
//            try {
//                emailService.sendOrderEmail(order.getCustomerEmail(), "ORDER-" + order.getId(), model);
//            } catch (MessagingException e) {
//                System.out.println(e.getMessage());
//                throw new RuntimeException(e);
//            }
//        }
        orderRepository.save(order);
    }


    public OrderDto confirmOrderReceived(Long customerId, Long orderId) {
        var order = orderRepository.getOneOrderWithItems(orderId).orElseThrow(OrderNotFoundException::new);

        if(!order.isPlacedBy(customerId)) {
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
