package com.roofiahmad.springstoreapp.orders;

import com.roofiahmad.springstoreapp.common.EmailService;
import com.roofiahmad.springstoreapp.payments.PaymentResult;
import com.roofiahmad.springstoreapp.payments.PaymentStatus;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final EmailService emailService;

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

    @Transactional
    public void updatePaymentStatus(PaymentResult paymentResult) {
        var order = orderRepository.findById(paymentResult.getOrderId()).orElseThrow(OrderNotFoundException::new);
        order.setStatus(paymentResult.getPaymentStatus());

        if(paymentResult.getPaymentStatus() == PaymentStatus.PAID) {
            // send order confirmation email
            var customer = order.getCustomer();
            Map<String, Object> model = new HashMap<>();
            model.put("customerName", customer.getName());
            model.put("orderNumber", "REF-" + Year.now().getValue() + "-" + order.getId());
            model.put("totalAmount", order.getTotalPrice());

            model.put("items", order.getItems().stream().map(p -> Map.of(
                            "name", p.getProduct().getName(),
                            "quantity", p.getQuantity(),
                            "price", p.getTotalPrice()
                    )
            ).toList());

            try {
                emailService.sendOrderEmail(order.getCustomerEmail(), "ORDER-" + order.getId(), model);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }


        orderRepository.save(order);
    }
}
