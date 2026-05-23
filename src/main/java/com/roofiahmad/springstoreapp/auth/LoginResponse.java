package com.roofiahmad.springstoreapp.auth;

import com.roofiahmad.springstoreapp.users.dtos.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LoginResponse {
    private final String tokenType = "Bearer";
    private String accessToken;
    private String refreshToken;
    private UserDto user;
}
