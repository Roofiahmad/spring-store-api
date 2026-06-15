package com.roofiahmad.springstoreapp.feature.profile;

import com.roofiahmad.springstoreapp.feature.profile.dto.ProfileDto;
import com.roofiahmad.springstoreapp.feature.profile.dto.UpdateProfileRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/profile")
@Tag(name = "Profile")
public class ProfileController {
    ProfileService profileService;

    @GetMapping("")
    public ResponseEntity<ProfileDto> me() {
        return ResponseEntity.ok(profileService.getProfile());
    }

    @PutMapping("")
    public ResponseEntity<ProfileDto> updateProfile(@Valid @RequestBody UpdateProfileRequest request) {
        return ResponseEntity.ok(profileService.updateProfile(request));
    }
}
