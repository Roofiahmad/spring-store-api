package com.roofiahmad.springstoreapp.users;

import com.roofiahmad.springstoreapp.auth.Role;
import com.roofiahmad.springstoreapp.common.BadRequestException;
import com.roofiahmad.springstoreapp.common.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public List<UserDto> getAllUsers(String sort) {
        if(!Set.of("name", "email").contains(sort))
            sort = "name";

        return userRepository.findAll(Sort.by(sort)).stream()
                .map(userMapper::toDto)
                .toList();
    }

    public UserDto getUser(Long id) {
        var user = userRepository.findById(id).orElseThrow(()-> new NotFoundException("User not found"));
       return userMapper.toDto(user);
    }

    public UserDto registerUser(RegisterUserRequest request) {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already registered");
        }

        var userEntity = userMapper.toEntity(request);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRole(Role.USER);

        userEntity = userRepository.save(userEntity);
        return userMapper.toDto(userEntity);
    }

    public UserDto updateUser(Long id, UpdateUserRequest request) {
        var user = userRepository.findById(id).orElseThrow(()-> new NotFoundException("User not found"));

        userMapper.update(request, user);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    public void deleteUser(Long id) {
        var userEntity = userRepository.findById(id).orElseThrow(()-> new NotFoundException("User not found"));
        userRepository.delete(userEntity);
    }

    public void changePassword(Long id, ChangePasswordRequest request)  {
        var user = userRepository.findById(id).orElseThrow(()-> new NotFoundException("User not found"));

        if(!passwordEncoder.matches(request.getOldPassword(), user.getPassword())){
            throw new BadRequestException("Old password does not match");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
}
