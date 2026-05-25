package com.roofiahmad.springstoreapp.auth;

import com.roofiahmad.springstoreapp.users.dtos.UserDto;
import com.roofiahmad.springstoreapp.users.entity.User;
import com.roofiahmad.springstoreapp.users.mappers.UserMapper;
import com.roofiahmad.springstoreapp.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private UserRepository userRepository;
    private JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findUserByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return LoginResponse.builder().accessToken(accessToken.toString()).refreshToken(refreshToken.toString()).user(userMapper.toDto(user)).build();
    }

    public String refreshAccessToken(String refreshToken) {
        var jwtRefreshToken = jwtService.parseToken(refreshToken);
        if(jwtRefreshToken== null || jwtRefreshToken.isExpired()) {
            throw new BadCredentialsException("Invalid refresh token");
        }

        var userId = jwtRefreshToken.getUserPrincipalFromRefreshToken(userRepository).getId();
        var user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return jwtService.generateAccessToken(user).toString();
    }

    public Optional<UserDto> getMe(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BadCredentialsException("Unauthorized");
        }

        var user = (UserPrincipal) authentication.getPrincipal();

        if(user != null) {
            var userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setRole(user.getRole());
            userDto.setEmail(user.getEmail());
            userDto.setName(user.getName());

            return Optional.of(userDto);
        }
        return Optional.empty();
    }
}
