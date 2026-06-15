package com.roofiahmad.springstoreapp.feature.user.mapper;

import com.roofiahmad.springstoreapp.feature.user.dto.RegisterUserRequest;
import com.roofiahmad.springstoreapp.feature.user.dto.UpdateUserRequest;
import com.roofiahmad.springstoreapp.feature.user.dto.UserDto;
import com.roofiahmad.springstoreapp.feature.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(RegisterUserRequest request);
    void update(UpdateUserRequest request, @MappingTarget User user);


}
