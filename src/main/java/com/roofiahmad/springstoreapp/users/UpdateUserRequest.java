package com.roofiahmad.springstoreapp.users;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String name;
    private String email;
}
