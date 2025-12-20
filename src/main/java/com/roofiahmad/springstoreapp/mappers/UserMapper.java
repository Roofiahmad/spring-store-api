package com.roofiahmad.springstoreapp.mappers;

import com.roofiahmad.springstoreapp.dtos.UserDto;
import com.roofiahmad.springstoreapp.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
}
