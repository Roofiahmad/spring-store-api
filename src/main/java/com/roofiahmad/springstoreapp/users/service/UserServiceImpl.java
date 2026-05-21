package com.roofiahmad.springstoreapp.users.service;

import com.roofiahmad.springstoreapp.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       var user = userRepository.findUserByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("user not found"));

       return new User(
               user.getEmail(),
               user.getPassword(),
               Collections.emptyList()
       );
    }
}
