package com.example.developpezlebackendenutilisantjavaetspring.controller;

import com.example.developpezlebackendenutilisantjavaetspring.dto.LoginDTO;
import com.example.developpezlebackendenutilisantjavaetspring.dto.RegisterDTO;
import com.example.developpezlebackendenutilisantjavaetspring.model.User;
import com.example.developpezlebackendenutilisantjavaetspring.response.AuthResponse;
import com.example.developpezlebackendenutilisantjavaetspring.security.JWTGenerator;
import com.example.developpezlebackendenutilisantjavaetspring.security.UserDetailsImpl;
import com.example.developpezlebackendenutilisantjavaetspring.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JWTGenerator jwtGenerator;
    private final UserService userService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JWTGenerator jwtGenerator, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDTO registerDTO) {
        if (userService.existsByEmail(registerDTO.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        userService.register(registerDTO);

        return authenticateAndGenerateToken(registerDTO.getEmail(), registerDTO.getPassword());
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginDTO loginDto) {
        return authenticateAndGenerateToken(loginDto.getEmail(), loginDto.getPassword());
    }

    @GetMapping("/me")
    public ResponseEntity<?> getUser(Authentication authentication) {
        if (authentication == null) return ResponseEntity.badRequest().build();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(buildUser(userDetails));
    }

    private static User buildUser(UserDetailsImpl userDetails) {
        return new User(
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getName(),
                userDetails.getCreatedAt(),
                userDetails.getUpdatedAt());
    }

    private ResponseEntity<AuthResponse> authenticateAndGenerateToken(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        String token = jwtGenerator.generateToken(email);

        return ResponseEntity.ok(new AuthResponse(token));
    }
}

