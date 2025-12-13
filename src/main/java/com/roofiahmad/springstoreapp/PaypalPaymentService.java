package com.roofiahmad.springstoreapp;

//@Service("paypal")
public class PaypalPaymentService  implements PaymentService {

    public PaypalPaymentService() {
        System.out.println("PaypalPaymentService constructor");
    }

    @Override
    public void processPayment(double amount) {
        System.out.println("PAYPAL");
        System.out.println("Amount: " + amount);
    }
}
