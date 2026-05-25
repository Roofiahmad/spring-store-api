package com.roofiahmad.springstoreapp.users.service;

import com.roofiahmad.springstoreapp.auth.Role;
import com.roofiahmad.springstoreapp.carts.Cart;
import com.roofiahmad.springstoreapp.carts.CartRepository;
import com.roofiahmad.springstoreapp.common.BadRequestException;
import com.roofiahmad.springstoreapp.common.NotFoundException;
import com.roofiahmad.springstoreapp.users.dtos.ChangePasswordRequest;
import com.roofiahmad.springstoreapp.users.dtos.RegisterUserRequest;
import com.roofiahmad.springstoreapp.users.dtos.UpdateUserRequest;
import com.roofiahmad.springstoreapp.users.dtos.UserDto;
import com.roofiahmad.springstoreapp.users.mappers.UserMapper;
import com.roofiahmad.springstoreapp.users.repository.UserRepository;
import com.roofiahmad.springstoreapp.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final CartRepository cartRepository;

    public List<UserDto> getAllUsers(String sort) {
        if(!Set.of("name", "email").contains(sort))
            sort = "name";

        return userRepository.findAllUsersAsDto(Sort.by(sort));

    }

    public UserDto getUser(Long id) {
        var user = userRepository.findById(id).orElseThrow(()-> new NotFoundException("User not found"));
       return userMapper.toDto(user);
    }

    @Transactional
    public UserDto registerUser(RegisterUserRequest request) {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already registered");
        }

        var userEntity = userMapper.toEntity(request);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRole(Role.USER);

        userEntity = userRepository.save(userEntity);

        Cart cart = new Cart();
        cart.setUser(userEntity);
        cartRepository.save(cart);

        return userMapper.toDto(userEntity);
    }

    public UserDto updateUser(UpdateUserRequest request) {
        var userPrincipal = Utils.getUserPrincipal();
        var user = userRepository.findById(userPrincipal.getId()).orElseThrow(()-> new NotFoundException("User not found"));

        userMapper.update(request, user);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    public void deleteUser() {
        var userPrincipal = Utils.getUserPrincipal();
        var userEntity = userRepository.findById(userPrincipal.getId()).orElseThrow(()-> new NotFoundException("User not found"));
        userRepository.delete(userEntity);
    }

    public void changePassword(ChangePasswordRequest request)  {
        var userPrincipal = Utils.getUserPrincipal();
        var user = userRepository.findById(userPrincipal.getId()).orElseThrow(()-> new NotFoundException("User not found"));

        if(!passwordEncoder.matches(request.getOldPassword(), user.getPassword())){
            throw new BadRequestException("Old password does not match");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
}
