package com.roofiahmad.springstoreapp.feature.auth.dto;

import com.roofiahmad.springstoreapp.feature.user.dto.UserDto;
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
