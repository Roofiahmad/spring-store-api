package com.roofiahmad.springstoreapp.address.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateAddressRequest {

    @NotBlank(message = "Street is required")
    @Size(max = 255, message = "Street must be less than 255 characters")
    private String street;

    @NotBlank(message = "City is required")
    @Size(max = 255, message = "City must be less than 255 characters")
    private String city;

    @NotBlank(message = "State is required")
    @Size(max = 255, message = "State must be less than 255 characters")
    private String state;

    @NotBlank(message = "Zip code is required")
    @Pattern(
            regexp = "^[0-9]{5,6}$",
            message = "Zip code must be a valid 5 or 6 digit number"
    )
    private String zip;

    @NotBlank(message = "Label is required")
    @Size(max = 255, message = "Label must be less than 255 characters")
    private String label;

    @NotNull(message = "Primary status configuration is required")
    private Boolean isPrimary;
}
