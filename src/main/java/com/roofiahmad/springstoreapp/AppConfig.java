package com.roofiahmad.springstoreapp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class AppConfig {
    @Value("${application.payment-gateway}")
    private String paymentGateway;

    @Bean
    @Lazy
    public PaymentService stripe(){
        return new StripePaymentService();
    }

    @Bean
    @Lazy
    public PaymentService paypal(){
        return new PaypalPaymentService();
    }

    @Bean
    @Lazy
    public OrderService orderService(){
        if(paymentGateway.equals("STRIPE")){
            return new OrderService(stripe());
        } else {
            return new OrderService(paypal());
        }
    }
}
