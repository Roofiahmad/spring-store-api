package com.roofiahmad.springstoreapp.feature.payment.gateway;

import com.roofiahmad.springstoreapp.feature.order.Order;
import com.roofiahmad.springstoreapp.feature.payment.CheckoutSession;
import com.roofiahmad.springstoreapp.feature.payment.PaymentResult;
import com.roofiahmad.springstoreapp.feature.payment.dto.WebhookRequest;

import java.util.Optional;

public interface PaymentGateway {
    CheckoutSession createCheckoutSession(Order order);
   Optional<PaymentResult> parseWebhookRequest(WebhookRequest request);
}
