package com.roofiahmad.springstoreapp.feature.payment;

public enum PaymentStatus {
    PENDING,
    PAID,
    FAILED,
    CANCELLED,
    PROCESSED,
    SHIPPED,
    DELIVERED;


    public boolean canTransitionTo(PaymentStatus nextStatus) {
        if (nextStatus == null) return false;
        return this.compareTo(nextStatus) <= 0;
    }
}
