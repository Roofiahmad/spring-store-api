package com.roofiahmad.springstoreapp.repositories;

import com.roofiahmad.springstoreapp.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}
