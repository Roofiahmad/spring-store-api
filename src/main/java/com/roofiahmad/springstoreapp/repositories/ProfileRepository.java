package com.roofiahmad.springstoreapp.repositories;

import com.roofiahmad.springstoreapp.entities.Profile;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<Profile, Long> {
}
