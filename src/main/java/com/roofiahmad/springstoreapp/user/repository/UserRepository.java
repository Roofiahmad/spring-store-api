package com.roofiahmad.springstoreapp.user.repository;

import com.roofiahmad.springstoreapp.auth.constant.Role;
import com.roofiahmad.springstoreapp.user.dto.UserDto;
import com.roofiahmad.springstoreapp.user.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByEmail(String email);

    Optional<User> findUserByEmail(String email);

    @EntityGraph(attributePaths = {"addresses", "profile"})
    @Query("SELECT u FROM User u WHERE u.id =:id")
    Optional<User> findUserAndProfileById(@Param("id") Long id);

    Boolean existsByEmailAndRole(String email, Role role);

    @Query("SELECT new com.roofiahmad.springstoreapp.user.dto.UserDto(u.id, u.name, u.email, u.role) FROM User u")
    List<UserDto> findAllUsersAsDto(Sort sort);
}
