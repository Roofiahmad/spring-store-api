package com.roofiahmad.springstoreapp.mappers;

import com.roofiahmad.springstoreapp.dtos.RegisterUserRequest;
import com.roofiahmad.springstoreapp.dtos.UpdateUserRequest;
import com.roofiahmad.springstoreapp.dtos.UserDto;
import com.roofiahmad.springstoreapp.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(RegisterUserRequest request);
    void update(UpdateUserRequest request,@MappingTarget User user);
}
