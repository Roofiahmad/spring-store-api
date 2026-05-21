package com.roofiahmad.springstoreapp.users.mappers;

import com.roofiahmad.springstoreapp.users.entity.User;
import com.roofiahmad.springstoreapp.users.dtos.RegisterUserRequest;
import com.roofiahmad.springstoreapp.users.dtos.UpdateUserRequest;
import com.roofiahmad.springstoreapp.users.dtos.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(RegisterUserRequest request);
    void update(UpdateUserRequest request, @MappingTarget User user);
}
