package com.roofiahmad.springstoreapp.services;

import com.roofiahmad.springstoreapp.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;


}
