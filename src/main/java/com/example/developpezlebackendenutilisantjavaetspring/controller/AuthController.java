package com.example.developpezlebackendenutilisantjavaetspring.controller;

import com.example.developpezlebackendenutilisantjavaetspring.dto.LoginDTO;
import com.example.developpezlebackendenutilisantjavaetspring.dto.RegisterDTO;
import com.example.developpezlebackendenutilisantjavaetspring.response.AuthResponse;
import com.example.developpezlebackendenutilisantjavaetspring.response.UserResponse;
import com.example.developpezlebackendenutilisantjavaetspring.security.JWTGenerator;
import com.example.developpezlebackendenutilisantjavaetspring.security.UserDetailsImpl;
import com.example.developpezlebackendenutilisantjavaetspring.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Registration", description = "Register a new user, returns JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registration successful",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request - Email already exists", content = @Content)})
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDTO registerDTO) {
        if (userService.existsByEmail(registerDTO.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        userService.register(registerDTO);

        return authenticateAndGenerateToken(registerDTO.getEmail(), registerDTO.getPassword());
    }

    @PostMapping("/login")
    @Operation(summary = "Authentication", description = "Authentication with email and password, returns JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)})
    public ResponseEntity<AuthResponse> login(@RequestBody LoginDTO loginDto) {
        return authenticateAndGenerateToken(loginDto.getEmail(), loginDto.getPassword());
    }

    @GetMapping("/me")
    @Operation(summary = "Get user", description = "Get details about current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)})
    public ResponseEntity<?> getUser(Authentication authentication) {
        if (authentication == null) return ResponseEntity.badRequest().build();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(buildUser(userDetails));
    }

    private UserResponse buildUser(UserDetailsImpl userDetails) {
        return new UserResponse(
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

