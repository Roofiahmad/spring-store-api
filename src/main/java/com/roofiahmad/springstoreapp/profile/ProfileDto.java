package com.roofiahmad.springstoreapp.profile;

import com.roofiahmad.springstoreapp.address.AddressDto;
import com.roofiahmad.springstoreapp.auth.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProfileDto {
    private Long id;
    private String name;
    private String email;
    private Role role;
    private List<AddressDto> addresses;
    private String bio;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private int loyaltyPoints;
}
