package com.example.developpezlebackendenutilisantjavaetspring.controllers;

import com.example.developpezlebackendenutilisantjavaetspring.dto.LoginDTO;
import com.example.developpezlebackendenutilisantjavaetspring.dto.RegisterDTO;
import com.example.developpezlebackendenutilisantjavaetspring.responses.AuthResponse;
import com.example.developpezlebackendenutilisantjavaetspring.responses.MessageError;
import com.example.developpezlebackendenutilisantjavaetspring.responses.UserResponse;
import com.example.developpezlebackendenutilisantjavaetspring.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @Operation(summary = "Registration", description = "Register a new user, returns JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registration successful",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request - Email already exists", content = @Content)})
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDTO registerDTO, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(new HashMap<>());
        }
        if (authService.existsByEmail(registerDTO.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        authService.register(registerDTO);
        authService.authenticate(registerDTO.getEmail(), registerDTO.getPassword());

        return ResponseEntity.ok(new AuthResponse(authService.generateToken(registerDTO.getEmail())));
    }

    @PostMapping("/login")
    @Operation(summary = "Authentication", description = "Authentication with email and password, returns JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)})
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDto, Errors errors) {
        if (errors.hasErrors()) {
            return new ResponseEntity<>(new MessageError("error"), HttpStatus.UNAUTHORIZED);
        }
        authService.authenticate(loginDto.getEmail(), loginDto.getPassword());
        String token = authService.generateToken(loginDto.getEmail());
        if (token.isEmpty()) {
            return new ResponseEntity<>(new MessageError("error"), HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(new AuthResponse(token));
    }

    @GetMapping("/me")
    @Operation(summary = "Get user", description = "Get details about current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)})
    public ResponseEntity<?> getUser(Principal principal) {
        return ResponseEntity.ok().body(authService.getUserByEmail(principal.getName()));
    }
}
