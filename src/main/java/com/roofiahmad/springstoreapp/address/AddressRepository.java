package com.roofiahmad.springstoreapp.address;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AddressRepository extends CrudRepository<Address, Integer> {

    List<Address> getAddressesByUserId(Long userId);
    Address getAddressById(Long addressId);
}
