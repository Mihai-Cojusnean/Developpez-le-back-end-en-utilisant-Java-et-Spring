package com.example.developpezlebackendenutilisantjavaetspring.controllers;

import com.example.developpezlebackendenutilisantjavaetspring.dto.LoginDTO;
import com.example.developpezlebackendenutilisantjavaetspring.dto.RegisterDTO;
import com.example.developpezlebackendenutilisantjavaetspring.models.User;
import com.example.developpezlebackendenutilisantjavaetspring.responses.AuthResponse;
import com.example.developpezlebackendenutilisantjavaetspring.responses.MessageResponse;
import com.example.developpezlebackendenutilisantjavaetspring.responses.UserResponse;
import com.example.developpezlebackendenutilisantjavaetspring.services.AuthService;
import com.example.developpezlebackendenutilisantjavaetspring.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final ModelMapper modelMapper;
    private final AuthService authService;
    private final UserService userService;

    @Autowired
    public AuthController(ModelMapper modelMapper, AuthService authService, UserService userService) {
        this.modelMapper = modelMapper;
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/register")
    @Operation(summary = "Registration", description = "Register a new user, returns JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registration successful",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request - Email already exists", content = @Content)})
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDTO registerDTO, Errors errors) {
        if (errors.hasErrors() || userService.userExists(registerDTO.getEmail())) {
            return ResponseEntity.badRequest().body(new HashMap<>());
        }
        userService.register(registerDTO);

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
            return new ResponseEntity<>(new MessageResponse("error"), HttpStatus.UNAUTHORIZED);
        }
        try {
            authService.authenticate(loginDto.getEmail(), loginDto.getPassword());
        } catch (BadCredentialsException ex) {
            return new ResponseEntity<>(new MessageResponse("error"), HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(new AuthResponse(authService.generateToken(loginDto.getEmail())));
    }

    @GetMapping("/me")
    @Operation(summary = "Get user", description = "Get details about current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns user details",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)})
    public ResponseEntity<?> getUser(Principal principal) {
        UserResponse userResponse = new UserResponse();
        try {
            User user = userService.getUserByEmail(principal.getName());
            modelMapper.map(user, userResponse);
        } catch (UsernameNotFoundException ex) {
            return new ResponseEntity<>(new HashMap<>(), HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok().body(userResponse);
    }
}
