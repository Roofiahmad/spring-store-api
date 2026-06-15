package com.roofiahmad.springstoreapp.feature.profile;

import com.roofiahmad.springstoreapp.feature.profile.dto.ProfileDto;
import com.roofiahmad.springstoreapp.feature.profile.dto.UpdateProfileRequest;
import com.roofiahmad.springstoreapp.feature.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    @Mapping(source = "profile.", target = ".")
    ProfileDto toProfileDto(User user);

    void update(UpdateProfileRequest request, @MappingTarget Profile profile);

}
