package com.roofiahmad.springstoreapp.users.dtos;

import com.roofiahmad.springstoreapp.auth.Role;
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
