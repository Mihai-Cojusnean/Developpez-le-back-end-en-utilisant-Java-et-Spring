package com.example.developpezlebackendenutilisantjavaetspring.services;

import com.example.developpezlebackendenutilisantjavaetspring.dto.RegisterDTO;
import com.example.developpezlebackendenutilisantjavaetspring.models.User;

public interface UserService {

    /**
     * Retrieves user information for the specified user ID.
     *
     * @param id  The unique identifier of the user to retrieve.
     * @return  A {@link User} object containing user details.
     */
    User getUserById(int id);

    /**
     * Retrieves user details for the given email.
     *
     * @param email  The user's email address.
     * @return  A {@link User} object containing user details.
     */
    User getUserByEmail(String email);

    /**
     * Registers a new user based on the provided registration details.
     *
     * @param registerDTO  The registration details.
     */
    void register(RegisterDTO registerDTO);

    /**
     * Checks if a user with the given email exists.
     *
     * @param email  The email to check.
     * @return  true if a user with the specified email exists; false otherwise.
     */
    boolean userExists(String email);
}
