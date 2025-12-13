package com.roofiahmad.springstoreapp;

//@Service("stripe")
//@Primary
public class StripePaymentService implements PaymentService {
    public StripePaymentService() {
        System.out.println("Stripe payment service created");
    }

    @Override
    public void processPayment(double amount){
        System.out.println("STRIPE");
        System.out.println("Amount " + amount);
    }
}
