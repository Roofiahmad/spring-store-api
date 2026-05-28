package com.roofiahmad.springstoreapp.payments;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.UUID;

@Data
public class CheckoutRequest {
    @NotNull(message = "Cart identifier is required")
    private UUID cartId;

    @NotBlank(message = "Full name is required")
    @Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters")
    private String fullName;

    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^\\+?[0-9]{9,15}$",
            message = "Phone number must be valid (between 9 and 15 digits, optionally starting with +)"
    )
    private String phoneNumber;

    @NotBlank(message = "Email address is required")
    @Email(message = "Please provide a valid email address layout")
    @Size(max = 255, message = "Email must be less than 255 characters")
    private String email;

    @NotNull(message = "Shipping address is required")
    private Integer shippingAddressId;
}
