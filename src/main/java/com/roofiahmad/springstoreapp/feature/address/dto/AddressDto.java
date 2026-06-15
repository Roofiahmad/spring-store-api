package com.roofiahmad.springstoreapp.feature.address.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddressDto {
    private Long id;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String label;
    private Boolean isPrimary;
}
