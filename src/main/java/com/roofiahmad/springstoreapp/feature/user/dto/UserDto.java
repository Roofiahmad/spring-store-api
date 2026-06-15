package com.roofiahmad.springstoreapp.feature.user.dto;

import com.roofiahmad.springstoreapp.feature.auth.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private Role role;
}
