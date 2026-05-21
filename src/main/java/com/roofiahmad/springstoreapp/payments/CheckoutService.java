package com.roofiahmad.springstoreapp.payments;

import com.roofiahmad.springstoreapp.auth.UserPrincipal;
import com.roofiahmad.springstoreapp.carts.CartRepository;
import com.roofiahmad.springstoreapp.carts.CartService;
import com.roofiahmad.springstoreapp.orders.*;
import com.roofiahmad.springstoreapp.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CheckoutService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final CartRepository cartRepository;
    private final PaymentGateway paymentGateway;
    private final OrderService orderService;


    @Transactional
    public CheckoutResponse checkout(CheckoutRequest request, UserPrincipal principal) throws PaymentException {
        var cart = cartRepository.getCartWithItems(request.getCartId()).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }

        if(cart.isEmpty()) {
           throw new CartEmptyException();
        }

        var customer = userRepository.findById(principal.getId()).orElseThrow();
        var order = Order.fromCart(cart, customer);
        orderRepository.save(order);

      try{
          var session = paymentGateway.createCheckoutSession(order);
          cartService.clearCart(cart.getId());
          return new CheckoutResponse(order.getId(), session.getCheckoutUrl());

      } catch (PaymentException e) {
          orderRepository.delete(order);
          throw e;
      }
    }

    public void handleWebhookEvent(WebhookRequest request) {
       paymentGateway.parseWebhookRequest(request).ifPresent(orderService::updatePaymentStatus);
    }
}
