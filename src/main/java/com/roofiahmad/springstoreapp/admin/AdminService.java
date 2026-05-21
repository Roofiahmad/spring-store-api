package com.roofiahmad.springstoreapp.admin;

import com.roofiahmad.springstoreapp.auth.Role;
import com.roofiahmad.springstoreapp.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AdminService {
    private final UserRepository userRepository;
    private final AdminMapper adminMapper;
    private final PasswordEncoder passwordEncoder;


    public AdminDto registerAdmin(RegisterAdminRequest request) {
        if(userRepository.existsByEmailAndRole(request.getEmail(), Role.ADMIN)){
            throw new RuntimeException("Email already registered");
        }

        var adminEntity = adminMapper.toEntity(request);
        adminEntity.setPassword(passwordEncoder.encode(adminEntity.getPassword()));
        adminEntity.setRole(Role.ADMIN);

        adminEntity = userRepository.save(adminEntity);
        return adminMapper.toDto(adminEntity);
    }

}
