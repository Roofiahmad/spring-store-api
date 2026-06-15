package com.roofiahmad.springstoreapp.feature.profile;

import com.roofiahmad.springstoreapp.infra.web.exception.NotFoundException;
import com.roofiahmad.springstoreapp.feature.profile.dto.ProfileDto;
import com.roofiahmad.springstoreapp.feature.profile.dto.UpdateProfileRequest;
import com.roofiahmad.springstoreapp.feature.user.repository.UserRepository;
import com.roofiahmad.springstoreapp.infra.util.Utils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ProfileService {
    private final ProfileRepository profileRepository;
    private UserRepository userRepository;
    private ProfileMapper profileMapper;

    public ProfileDto getProfile(){
        var userPrincipal = Utils.getUserPrincipal();
        var user = userRepository.findUserAndProfileById(userPrincipal.getId()).orElseThrow(() -> new NotFoundException("User not found"));
        return profileMapper.toProfileDto(user);
    }

    public ProfileDto updateProfile(UpdateProfileRequest request){
        var userPrincipal = Utils.getUserPrincipal();
        var user = userRepository.findUserAndProfileById(userPrincipal.getId()).orElseThrow(() -> new NotFoundException("User not found"));
        Profile profile = user.getProfile();
        profileMapper.update(request,profile);

        profileRepository.save(profile);
        user.setProfile(profile);

        return profileMapper.toProfileDto(user);
    }
}
