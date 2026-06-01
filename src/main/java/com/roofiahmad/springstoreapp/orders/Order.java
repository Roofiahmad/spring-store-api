package com.roofiahmad.springstoreapp.orders;

import com.roofiahmad.springstoreapp.address.AddressDto;
import com.roofiahmad.springstoreapp.carts.Cart;
import com.roofiahmad.springstoreapp.payments.PaymentStatus;
import com.roofiahmad.springstoreapp.users.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(name = "subtotal", nullable = false)
    private BigDecimal subTotal;

    @Column(name = "shipping_fee", nullable = false)
    private BigDecimal shippingFee;

    @Column(name = "vat_amount", nullable = false)
    private BigDecimal vatAmount;

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "shipping_address_snapshot", nullable = false)
    private AddressDto shippingAddressSnapshot;

    @Column(name = "customer_email", nullable = false)
    private String customerEmail;

    @Column(name = "customer_phone_number", nullable = false)
    private String customerPhoneNumber;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @BatchSize(size = 20)
    private Set<OrderItem> items = new LinkedHashSet<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("createdAt ASC")
    @BatchSize(size = 10)
    private List<OrderStatusHistory> statusHistory = new ArrayList<>();

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;


    public static Order fromCart(Cart cart, User customer, AddressDto addressSnapshot, BigDecimal defaultShippingFee, BigDecimal vatRate) {
        BigDecimal subTotal = cart.getSubTotal();
        BigDecimal calculatedVat = subTotal.multiply(vatRate);
        BigDecimal finalTotalPrice = subTotal.add(defaultShippingFee).add(calculatedVat);

        var order = new Order();
        order.setCustomer(customer);
        order.insertStatusHistory(PaymentStatus.PENDING, "Waiting user to paid");
        order.setSubTotal(subTotal);
        order.setShippingFee(defaultShippingFee);
        order.setVatAmount(calculatedVat);
        order.setShippingAddressSnapshot(addressSnapshot);
        order.setTotalPrice(finalTotalPrice);

        cart.getItems().forEach(item -> {
            var orderItem = new OrderItem(order, item.getProduct(), item.getQuantity());
            order.items.add(orderItem);
        });

        return order;
    }

    public boolean isPlacedBy(Long customerId) {
       return customer.getId().equals(customerId);
    }

    public void insertStatusHistory(PaymentStatus newStatus, String trackingNotes) {
        this.setStatus(newStatus);

        OrderStatusHistory historyEntry = new OrderStatusHistory(this, newStatus, trackingNotes);
        this.statusHistory.add(historyEntry);
    }

}