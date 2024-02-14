package com.example.developpezlebackendenutilisantjavaetspring.services;

import com.example.developpezlebackendenutilisantjavaetspring.dto.RegisterDTO;
import com.example.developpezlebackendenutilisantjavaetspring.responses.UserResponse;

public interface AuthService {
    /**
     * Registers a new user based on the provided registration details.
     *
     * @param registerDTO The registration details.
     */
    void register(RegisterDTO registerDTO);

    /**
     * Checks if a user with the given email exists.
     *
     * @param email The email to check.
     * @return true if a user with the specified email exists; false otherwise.
     */
    boolean existsByEmail(String email);

    /**
     * Generates a token for the user with the given email.
     *
     * @param email The user's email.
     * @return The generated token.
     */
    String generateToken(String email);

    /**
     * Retrieves user details for the given email.
     *
     * @param email The user's email address.
     * @return A {@link UserResponse} object containing user details.
     */
    UserResponse getUserByEmail(String email);

    /**
     * Authenticates a user with the provided email and password.
     *
     * @param email    The user's email.
     * @param password The user's password.
     */
    void authenticate(String email, String password);
}
