package com.roofiahmad.springstoreapp.payments;

import com.roofiahmad.springstoreapp.orders.Order;

import java.util.Optional;

public interface PaymentGateway {
    CheckoutSession createCheckoutSession(Order order);
   Optional<PaymentResult> parseWebhookRequest(WebhookRequest request);
}
