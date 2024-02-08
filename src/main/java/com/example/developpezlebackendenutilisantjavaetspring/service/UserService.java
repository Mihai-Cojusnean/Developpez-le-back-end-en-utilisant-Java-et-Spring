package com.example.developpezlebackendenutilisantjavaetspring.service;

import com.example.developpezlebackendenutilisantjavaetspring.dto.RegisterDTO;
import com.example.developpezlebackendenutilisantjavaetspring.model.User;
import com.example.developpezlebackendenutilisantjavaetspring.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepo;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepo) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
    }

    public void register(RegisterDTO registerDTO) {
        User user = new User(
                registerDTO.getEmail(),
                registerDTO.getName(),
                passwordEncoder.encode(registerDTO.getPassword()),
                LocalDateTime.now(),
                LocalDateTime.now());

        userRepo.save(user);
    }

    public boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }
}
