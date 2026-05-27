package com.roofiahmad.springstoreapp.address;

import com.roofiahmad.springstoreapp.auth.AuthenticationFailedException;
import com.roofiahmad.springstoreapp.common.NotFoundException;
import com.roofiahmad.springstoreapp.users.repository.UserRepository;
import com.roofiahmad.springstoreapp.utils.Utils;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AddressService {
    private final AddressMapper addressMapper;
    private final UserRepository userRepository;
    private AddressRepository addressRepository;

    private @NonNull Address getAddressEntity(Long addressId) {
        var userPrincipal = Utils.getUserPrincipal();
        var addressEntity = addressRepository.getAddressById(addressId);
        if (!addressEntity.getUser().getId().equals(userPrincipal.getId())) {
            throw new AuthenticationFailedException("Unauthorized");
        }
        return addressEntity;
    }


    public List<AddressDto> getAddresses() {
        var userPrincipal = Utils.getUserPrincipal();
        var address = addressRepository.getAddressesByUserId(userPrincipal.getId());
        return addressMapper.toListAddressDto(address);
    }

    public AddressDto addAddress(CreateAddressRequest request) {
        var userEntity = userRepository.findById(Utils.getUserPrincipal().getId()).orElseThrow(()-> new NotFoundException("User not found"));
        var addressEntity = addressMapper.toAddressEntity(request);

        addressEntity.setUser(userEntity);
        addressRepository.save(addressEntity);
        return addressMapper.toAddressDto(addressEntity);

    }

    public AddressDto updateAddress(Long addressId, UpdateAddressRequest request) {
        var addressEntity = getAddressEntity(addressId);

        addressMapper.update(request, addressEntity);
        addressRepository.save(addressEntity);

        return addressMapper.toAddressDto(addressEntity);

    }

    public void deleteAddress(Long addressId) {
        var addressEntity = getAddressEntity(addressId);
        addressRepository.delete(addressEntity);
    }



}
