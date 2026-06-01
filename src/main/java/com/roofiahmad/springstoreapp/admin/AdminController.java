package com.roofiahmad.springstoreapp.admin;

import com.roofiahmad.springstoreapp.products.PagedResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
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

//    @GetMapping("/orders")
//    public ResponseEntity<ApiResponseWrapper<PagedResponse<?>>> getOrdersAdmin() {
//        var pageable = PageRequest.of(0 / 8, 8, Sort.by("id").descending() );
//        var pageData = adminService.getOrdersAdmin(pageable);
//
//        return ResponseEntity.ok(ApiResponseWrapper.success(pageData));
//    }

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
