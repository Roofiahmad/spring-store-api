package com.roofiahmad.springstoreapp.controllers;

import com.roofiahmad.springstoreapp.dtos.ChangePasswordRequest;
import com.roofiahmad.springstoreapp.dtos.RegisterUserRequest;
import com.roofiahmad.springstoreapp.dtos.UpdateUserRequest;
import com.roofiahmad.springstoreapp.dtos.UserDto;
import com.roofiahmad.springstoreapp.mappers.UserMapper;
import com.roofiahmad.springstoreapp.repositories.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
@Tag(name = "Users")
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @GetMapping()
    public Iterable<UserDto> getAllUsers(
            @RequestHeader(required = false, defaultValue = "", name = "x-auth-token") Integer authToken,
           @RequestParam(required = false, defaultValue = "", name = "sort") String sort
    ) {

        System.out.println(authToken);

        if(!Set.of("name", "email").contains(sort))
            sort = "name";


        return userRepository.findAll(Sort.by(sort)).stream()
                .map(userMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable(name = "id") Long id) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @PostMapping()
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterUserRequest request, UriComponentsBuilder uriBuilder) {

        if(userRepository.existsByEmail(request.getEmail())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Email already registered"));
        }

       var userEntity = userMapper.toEntity(request);
        userEntity = userRepository.save(userEntity);
        var userDto = userMapper.toDto(userEntity);

        var uri = uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();

       return ResponseEntity.created(uri).body(userDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable(name = "id") Long id,@RequestBody UpdateUserRequest request) {
        var userEntity = userRepository.findById(id).orElse(null);
        if (userEntity == null) {
            return ResponseEntity.notFound().build();
        }

        userMapper.update(request, userEntity);
        userRepository.save(userEntity);

        return ResponseEntity.ok(userMapper.toDto(userEntity));

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable(name = "id") Long id) {
        var userEntity = userRepository.findById(id).orElse(null);
        if (userEntity == null) {
            return ResponseEntity.notFound().build();
        }

        userRepository.delete(userEntity);

        return ResponseEntity.ok("User deleted successfully");

    }

    @PostMapping("/{id}/change-password")
    public ResponseEntity<Void> changePassword(@PathVariable(name = "id") Long id, @RequestBody ChangePasswordRequest request) {
        var userEntity = userRepository.findById(id).orElse(null);
        if (userEntity == null)
            return ResponseEntity.notFound().build();

        if(!userEntity.getPassword().equals(request.getOldPassword()))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        userEntity.setPassword(request.getNewPassword());
        userRepository.save(userEntity);

        return ResponseEntity.noContent().build();
    }



}
