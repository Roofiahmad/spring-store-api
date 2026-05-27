package com.roofiahmad.springstoreapp.address;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateAddressRequest {

    @Size(max = 255, message = "Street must be less than 255 characters")
    private String street;

    @Size(max = 255, message = "City must be less than 255 characters")
    private String city;

    @Size(max = 255, message = "State must be less than 255 characters")
    private String state;

    @Pattern(
            regexp = "^[0-9]{5,6}$",
            message = "Zip code must be a valid 5 or 6 digit number"
    )
    private String zip;

    @Size(max = 255, message = "Label must be less than 255 characters")
    private String label;

    private Boolean isPrimary;
}
