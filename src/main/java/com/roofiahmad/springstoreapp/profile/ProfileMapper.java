package com.roofiahmad.springstoreapp.profile;

import com.roofiahmad.springstoreapp.profile.dto.ProfileDto;
import com.roofiahmad.springstoreapp.profile.dto.UpdateProfileRequest;
import com.roofiahmad.springstoreapp.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    @Mapping(source = "profile.", target = ".")
    ProfileDto toProfileDto(User user);

    void update(UpdateProfileRequest request, @MappingTarget Profile profile);

}
