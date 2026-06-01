package com.roofiahmad.springstoreapp.admin;

import com.roofiahmad.springstoreapp.auth.Role;
import com.roofiahmad.springstoreapp.common.NotFoundException;
import com.roofiahmad.springstoreapp.orders.Order;
import com.roofiahmad.springstoreapp.orders.OrderDto;
import com.roofiahmad.springstoreapp.orders.OrderMapper;
import com.roofiahmad.springstoreapp.orders.OrderRepository;
import com.roofiahmad.springstoreapp.payments.PaymentStatus;
import com.roofiahmad.springstoreapp.products.PagedResponse;
import com.roofiahmad.springstoreapp.products.PagedResponseMetadata;
import com.roofiahmad.springstoreapp.products.Product;
import com.roofiahmad.springstoreapp.products.ProductRepository;
import com.roofiahmad.springstoreapp.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@AllArgsConstructor
@Service
public class AdminService {
    private final UserRepository userRepository;
    private final AdminMapper adminMapper;
    private final PasswordEncoder passwordEncoder;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ProductRepository productRepository;


    public AdminDto registerAdmin(RegisterAdminRequest request) {
        if(userRepository.existsByEmailAndRole(request.getEmail(), Role.ADMIN)){
            throw new RuntimeException("Email already registered");
        }

        var adminEntity = adminMapper.toEntity(request);
        adminEntity.setPassword(passwordEncoder.encode(adminEntity.getPassword()));
        adminEntity.setRole(Role.ADMIN);

        adminEntity = userRepository.save(adminEntity);
        return adminMapper.toDto(adminEntity);
    }

    public PagedResponse<?> getOrdersAdmin(PaymentStatus status ,Pageable pageable) {
        Page<Order> orderPage = orderRepository.findAllByStatus(status,pageable);

        List<OrderDto> orderDtos = orderPage.getContent().stream()
                .map(orderMapper::toDto)
                .toList();

        PagedResponseMetadata metadata = PagedResponseMetadata.builder()
                .totalItems(orderPage.getTotalElements())
                .totalPages(orderPage.getTotalPages())
                .currentPage(orderPage.getNumber())
                .pageSize(orderPage.getSize())
                .build();

        return new PagedResponse<>(orderDtos, metadata);
    }

    public AdminOrderDto updateOrderStatus(Long id, @RequestBody AdminUpdateOrderRequest request) {
        Order order = orderRepository.getOneOrderById(id).orElseThrow(()-> new NotFoundException("Order not found"));

        if (!order.getStatus().canTransitionTo(request.getStatus())) {
            throw new IllegalArgumentException(
                    "Invalid status sequence: Cannot revert order status backward from ["
                            + order.getStatus() + "] to [" + request.getStatus() + "]."
            );
        }

        order.setStatus(request.getStatus());
        order = orderRepository.save(order);
        return adminMapper.toAdminOrderDto(order);
    }

    public AdminStatisticDto getAdminStatistics() {
        Long listedProducts = productRepository.countByStockGreaterThan(0);
        Long totalStockVolume = productRepository.getTotalStock();
        List<Product> listOutOfStockProducts = productRepository.findOutOfStockProducts();
        List<AdminProductDto> productOutOfStockDto = listOutOfStockProducts.stream()
                .map(adminMapper::toAdminProductDto)
                .toList();

        List<Order> orderPendingList = orderRepository.findByStatus(PaymentStatus.PAID);
        List<AdminOrderDto> orderPendingDto = orderPendingList.stream()
                .map(adminMapper::toAdminOrderDto)
                .toList();

        System.out.println(orderPendingList.size() + " size order pending list");
        System.out.println(listOutOfStockProducts.size() + " size out of stock products");

        return AdminStatisticDto.builder()
                .productShortages(productOutOfStockDto)
                .orderPendingFulfillment(orderPendingDto)
                .totalStockVolume(totalStockVolume)
                .listedProducts(listedProducts).build();
    }

    public PagedResponse<AdminProductDto> getAdminProducts(Short categoryId,Pageable pageable) {
        Page<Product> productPage = productRepository.findByCategory(categoryId,pageable);

        List<AdminProductDto> productDtos = productPage.getContent().stream()
                .map(adminMapper::toAdminProductDto)
                .toList();

        PagedResponseMetadata metadata = PagedResponseMetadata.builder()
                .totalItems(productPage.getTotalElements())
                .totalPages(productPage.getTotalPages())
                .currentPage(productPage.getNumber())
                .pageSize(productPage.getSize())
                .build();

        return new PagedResponse<>(productDtos, metadata);
    }


}
