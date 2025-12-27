package com.roofiahmad.springstoreapp.repositories;

import com.roofiahmad.springstoreapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByEmail(String email);

    Optional<User> findUserByEmail(String email);

    Object findUserById(Long id);
}
