package com.example.developpezlebackendenutilisantjavaetspring.services.implementation;

import com.example.developpezlebackendenutilisantjavaetspring.dto.RegisterDTO;
import com.example.developpezlebackendenutilisantjavaetspring.models.User;
import com.example.developpezlebackendenutilisantjavaetspring.repositories.UserRepository;
import com.example.developpezlebackendenutilisantjavaetspring.responses.UserResponse;
import com.example.developpezlebackendenutilisantjavaetspring.security.JWTGenerator;
import com.example.developpezlebackendenutilisantjavaetspring.services.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JWTGenerator jwtGenerator;
    private final UserRepository userRepo;
    private final ModelMapper modelMapper;

    public AuthServiceImpl(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UserRepository userRepo, JWTGenerator jwtGenerator, ModelMapper modelMapper) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
        this.jwtGenerator = jwtGenerator;
        this.modelMapper = modelMapper;
    }

    public void register(RegisterDTO registerDTO) {
        User user = new User();

        modelMapper.map(registerDTO, user);
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        userRepo.save(user);
    }

    public boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    public UserResponse getUserByEmail(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        UserResponse userResponse = new UserResponse();

        modelMapper.map(user, userResponse);

        return userResponse;
    }

    public String generateToken(String email) {
        Optional<User> user = userRepo.findByEmail(email);

        return user.isEmpty() ? "" : jwtGenerator.generateToken(email);
    }

    public void authenticate(String email, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));
    }
}
