package com.roofiahmad.springstoreapp.admin;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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
}
