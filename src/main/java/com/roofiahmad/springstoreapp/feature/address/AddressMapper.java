package com.roofiahmad.springstoreapp.feature.address;

import com.roofiahmad.springstoreapp.feature.address.dto.AddressDto;
import com.roofiahmad.springstoreapp.feature.address.dto.CreateAddressRequest;
import com.roofiahmad.springstoreapp.feature.address.dto.UpdateAddressRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = org.mapstruct.NullValueCheckStrategy.ALWAYS
)
public interface AddressMapper {
    List<AddressDto> toListAddressDto(List<Address> addresses);

    Address toAddressEntity(CreateAddressRequest request);

    AddressDto toAddressDto(Address address);

    void update(UpdateAddressRequest request, @MappingTarget Address address);
}
