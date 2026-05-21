package com.roofiahmad.springstoreapp.auth;

import com.roofiahmad.springstoreapp.users.dtos.UserDto;
import com.roofiahmad.springstoreapp.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private JwtService jwtService;

    public Map<String, Jwt> login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        var user = userRepository.findUserByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken
        );
    }

    public String refreshAccessToken(String refreshToken) {
        var jwtRefreshToken = jwtService.parseToken(refreshToken);
        if(jwtRefreshToken== null || jwtRefreshToken.isExpired()) {
            throw new BadCredentialsException("Invalid refresh token");
        }

        var userId = jwtRefreshToken.getUserPrincipal().getId();
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
            userDto.setEmail(user.getEmail());
            userDto.setName(user.getName());

            return Optional.of(userDto);
        }
        return Optional.empty();
    }
}
