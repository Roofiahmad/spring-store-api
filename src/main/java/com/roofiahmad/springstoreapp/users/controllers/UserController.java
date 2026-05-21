package com.roofiahmad.springstoreapp.users.controllers;

import com.roofiahmad.springstoreapp.users.dtos.ChangePasswordRequest;
import com.roofiahmad.springstoreapp.users.dtos.RegisterUserRequest;
import com.roofiahmad.springstoreapp.users.dtos.UpdateUserRequest;
import com.roofiahmad.springstoreapp.users.dtos.UserDto;
import com.roofiahmad.springstoreapp.users.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
@Tag(name = "Users")
public class UserController {

    private final UserService userService;

    @GetMapping()
    public List<UserDto> getAllUsers(
           @RequestParam(required = false, defaultValue = "", name = "sort") String sort
    ) {
        return userService.getAllUsers(sort);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable(name = "id") Long id) {
        var userDto = userService.getUser(id);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping()
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterUserRequest request, UriComponentsBuilder uriBuilder) {
        var userDto = userService.registerUser(request);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();
       return ResponseEntity.created(uri).body(userDto);
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteUser() {
        userService.deleteUser();
        return ResponseEntity.ok("User deleted successfully");
    }

    @PutMapping()
    public ResponseEntity<UserDto> updateUser(@RequestBody UpdateUserRequest request) {
        var userDto = userService.updateUser( request);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordRequest request) {
        userService.changePassword(request);
        return ResponseEntity.noContent().build();
    }

}
