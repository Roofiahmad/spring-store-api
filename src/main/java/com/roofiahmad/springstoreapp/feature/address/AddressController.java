package com.roofiahmad.springstoreapp.feature.address;

import com.roofiahmad.springstoreapp.feature.address.dto.AddressDto;
import com.roofiahmad.springstoreapp.feature.address.dto.CreateAddressRequest;
import com.roofiahmad.springstoreapp.feature.address.dto.UpdateAddressRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/address")
@Tag(name = "Address")
public class AddressController {
    private final AddressService addressService;

    @GetMapping("")
    public ResponseEntity<List<AddressDto>> getAddresses() {
        return ResponseEntity.ok(addressService.getAddresses());
    }

    @PostMapping("")
    public ResponseEntity<AddressDto> addAddress(@Valid @RequestBody CreateAddressRequest request) {
        return ResponseEntity.ok(addressService.addAddress(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDto> updateAddress(@PathVariable(name = "id") Long addressId,
                                                    @Valid @RequestBody UpdateAddressRequest request) {
        return ResponseEntity.ok(addressService.updateAddress(addressId,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable(name = "id") Long addressId) {
        addressService.deleteAddress(addressId);
        return ResponseEntity.noContent().build();
    }
}
