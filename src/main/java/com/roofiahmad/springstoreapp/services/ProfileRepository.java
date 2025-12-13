package com.roofiahmad.springstoreapp.services;

import com.roofiahmad.springstoreapp.entities.Profile;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<Profile, Long> {
}