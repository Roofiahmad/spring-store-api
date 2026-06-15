package com.roofiahmad.springstoreapp.feature.address;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AddressRepository extends CrudRepository<Address, Integer> {

    List<Address> getAddressesByUserId(Long userId);
    Address getAddressById(Long addressId);

    @Query("SELECT a FROM Address a WHERE a.user.id = :customerId AND a.isPrimary = TRUE ")
    Address getCustomerPrimaryAddress(Long customerId);
}
