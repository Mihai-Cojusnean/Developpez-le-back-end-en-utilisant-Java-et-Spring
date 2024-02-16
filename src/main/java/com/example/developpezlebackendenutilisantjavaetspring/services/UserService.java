package com.example.developpezlebackendenutilisantjavaetspring.services;

import com.example.developpezlebackendenutilisantjavaetspring.dto.auth.RegisterDTO;
import com.example.developpezlebackendenutilisantjavaetspring.dto.UserDTO;
import com.example.developpezlebackendenutilisantjavaetspring.exceptions.UserNotFoundException;

public interface UserService {

    /**
     * Retrieves user information for the specified user ID.
     *
     * @param id The unique identifier of the user to retrieve.
     * @return A {@link UserDTO} object containing user details.
     * @throws UserNotFoundException If user with provided ID does not exist.
     */
    UserDTO getUserById(int id);

    /**
     * Retrieves user details for the given email.
     *
     * @param email The user's email address.
     * @return A {@link UserDTO} object containing user details.
     * @throws UserNotFoundException If user with provided email does not exist.
     */
    UserDTO getUserByEmail(String email);

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
     * @return true If a user with the specified email exists; false otherwise.
     */
    boolean userExists(String email);
}
