package com.roofiahmad.springstoreapp.repositories;

import com.roofiahmad.springstoreapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByEmail(String email);
}
