package com.roofiahmad.springstoreapp.admin;

import com.roofiahmad.springstoreapp.users.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminMapper {
    AdminDto toDto(User user);
    User toEntity(RegisterAdminRequest request);
}
