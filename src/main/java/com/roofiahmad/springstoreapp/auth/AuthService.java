package com.roofiahmad.springstoreapp.auth;

import com.roofiahmad.springstoreapp.auth.dto.LoginRequest;
import com.roofiahmad.springstoreapp.auth.dto.LoginResponse;
import com.roofiahmad.springstoreapp.auth.jwt.JwtService;
import com.roofiahmad.springstoreapp.common.exception.NotFoundException;
import com.roofiahmad.springstoreapp.user.entity.User;
import com.roofiahmad.springstoreapp.user.mapper.UserMapper;
import com.roofiahmad.springstoreapp.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthService {
    private final UserMapper userMapper;
    private UserRepository userRepository;
    private JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findUserByEmail(request.getEmail())
                .orElseThrow(() -> new AuthenticationFailedException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AuthenticationFailedException("Invalid email or password");
        }

        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return LoginResponse.builder().accessToken(accessToken.toString()).refreshToken(refreshToken.toString()).user(userMapper.toDto(user)).build();
    }

    public String refreshAccessToken(String refreshToken) {
        var jwtRefreshToken = jwtService.parseToken(refreshToken);
        if(jwtRefreshToken== null || jwtRefreshToken.isExpired()) {
            throw new AuthenticationFailedException("Invalid refresh token");
        }

        var userId = jwtRefreshToken.getUserPrincipalFromRefreshToken(userRepository).getId();
        var user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        return jwtService.generateAccessToken(user).toString();
    }


}
