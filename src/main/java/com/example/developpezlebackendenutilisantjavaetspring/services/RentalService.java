package com.example.developpezlebackendenutilisantjavaetspring.services;

import com.example.developpezlebackendenutilisantjavaetspring.dto.rental.PutRentalDTO;
import com.example.developpezlebackendenutilisantjavaetspring.dto.rental.RentalDTO;
import com.example.developpezlebackendenutilisantjavaetspring.dto.rental.SaveRentalDTO;
import com.example.developpezlebackendenutilisantjavaetspring.exceptions.ResourceNotFoundException;
import com.example.developpezlebackendenutilisantjavaetspring.exceptions.UnauthorizedException;
import com.example.developpezlebackendenutilisantjavaetspring.responses.RentalsResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.security.Principal;

public interface RentalService {
    /**
     * Saves a rental.
     *
     * @param saveRentalDTO  The rental data transfer object.
     * @param authentication The user's authentication context.
     * @throws IOException If there are issues related to input/output operations.
     */
    void save(SaveRentalDTO saveRentalDTO,
              Authentication authentication,
              HttpServletRequest request) throws IOException;

    /**
     * Updates a rental with the specified ID.
     *
     * @param id           The ID of the rental to be updated.
     * @param putRentalDTO The DTO containing the updated rental information.
     * @param principal    Authenticated user details.
     * @throws UnauthorizedException If the user lacks necessary permissions.
     */
    void update(int id, PutRentalDTO putRentalDTO, Principal principal);

    /**
     * Retrieves all rentals.
     *
     * @return A list containing all rentals.
     */
    RentalsResponse getAll();

    /**
     * Retrieves a single {@link RentalDTO} object representing a rental entity based on its unique identifier.
     *
     * @param id The unique identifier of the rental to retrieve.
     * @return A {@link RentalDTO} object representing the retrieved rental.
     * @throws ResourceNotFoundException If the rental with the specified ID does not exist.
     */
    RentalDTO getById(Integer id);
}
