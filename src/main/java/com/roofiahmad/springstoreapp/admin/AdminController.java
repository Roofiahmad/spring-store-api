package com.roofiahmad.springstoreapp.admin;

import com.roofiahmad.springstoreapp.admin.dto.*;
import com.roofiahmad.springstoreapp.common.dto.ApiResponseWrapper;
import com.roofiahmad.springstoreapp.payment.PaymentStatus;
import com.roofiahmad.springstoreapp.common.dto.PagedResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@AllArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/hello")
    public String hello(){
        return "Hello Admin!";
    }

    @PostMapping()
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody RegisterAdminRequest request, UriComponentsBuilder uriBuilder) {
        var userDto = adminService.registerAdmin(request);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();
        return ResponseEntity.created(uri).body(userDto);
    }

    @GetMapping("/orders")
    public ResponseEntity<ApiResponseWrapper<PagedResponse<?>>> getOrdersAdmin(
            @RequestParam(value = "", required = false) PaymentStatus status,
            @PageableDefault(size = 5, page = 0, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        var pageData = adminService.getOrdersAdmin(status,pageable);

        return ResponseEntity.ok(ApiResponseWrapper.success(pageData));
    }

    @PutMapping("/order/{id}")
    public ResponseEntity<AdminOrderDto> updateOrderStatus(@PathVariable Long id, @Valid @RequestBody AdminUpdateOrderRequest request) {
        AdminOrderDto updatedOrder = adminService.updateOrderStatus(id, request);
        return ResponseEntity.ok(updatedOrder);
    }

    @GetMapping("/statistics")
    public ResponseEntity<AdminStatisticDto> getAdminStatistics() {
        var adminStatistics = adminService.getAdminStatistics();

        return ResponseEntity.ok(adminStatistics);
    }

    @GetMapping("/products")
    public ResponseEntity<PagedResponse<AdminProductDto>> getAdminProducts(
            @RequestParam(value = "categoryId", required = false) Short categoryId,
            @PageableDefault(size = 5, page = 0, sort = "createdAt") Pageable pageable
    ) {
        var adminProductDtos = adminService.getAdminProducts(categoryId,pageable);
        return ResponseEntity.ok(adminProductDtos);
    }
}
