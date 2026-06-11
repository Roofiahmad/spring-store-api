package com.roofiahmad.springstoreapp.common.service.payment;

import com.roofiahmad.springstoreapp.order.Order;
import com.roofiahmad.springstoreapp.payment.CheckoutSession;
import com.roofiahmad.springstoreapp.payment.PaymentResult;
import com.roofiahmad.springstoreapp.payment.dto.WebhookRequest;

import java.util.Optional;

public interface PaymentGateway {
    CheckoutSession createCheckoutSession(Order order);
   Optional<PaymentResult> parseWebhookRequest(WebhookRequest request);
}
