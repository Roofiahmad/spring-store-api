package com.roofiahmad.springstoreapp.feature.order;

import com.roofiahmad.springstoreapp.feature.payment.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = {"items.product"})
    @Query("SELECT o FROM Order o WHERE o.customer.id = :customerId ORDER BY o.createdAt DESC")
    List<Order> getOrdersByCustomer(@Param("customerId") Long customerId, Pageable pageable);

    @EntityGraph(attributePaths = {"items.product"})
    @Query("SELECT o FROM Order o WHERE o.id = :orderId")
    Optional<Order> getOneOrderWithItems(@Param("orderId") Long orderId);

    Optional<Order> getOneOrderById(Long orderId);

    List<Order> findByStatus(PaymentStatus status);

    @EntityGraph(attributePaths = {"customer.name"})
    @Query("""
    SELECT o FROM Order o 
    WHERE (:status IS NULL OR o.status = :status) 
        """)
    Page<Order> findAllByStatus(PaymentStatus status, Pageable pageable);

}