package com.example.developpezlebackendenutilisantjavaetspring.services;

import com.example.developpezlebackendenutilisantjavaetspring.dto.RentalDTO;
import com.example.developpezlebackendenutilisantjavaetspring.models.Rental;
import com.example.developpezlebackendenutilisantjavaetspring.responses.RentalResponse;
import org.springframework.security.core.Authentication;

import java.io.IOException;

public interface RentalService {
    /**
     * Saves a rental.
     *
     * @param rentalDTO       The rental data transfer object.
     * @param authentication  The user's authentication context.
     * @return                The saved {@link Rental}.
     * @throws IOException    If there are issues related to input/output operations.
     */
    Rental save(RentalDTO rentalDTO, Authentication authentication) throws IOException;

    /**
     * Retrieves all rentals associated with a specific owner.
     *
     * @param authentication  The owner's authentication context.
     * @return A {@link RentalResponse} containing all owner's rentals.
     */
    RentalResponse getAllByOwnerId(Authentication authentication);

    /**
     * Retrieves a specific rental by its unique identifier.
     *
     * @param id  The rental ID.
     * @return    The {@link Rental} corresponding to the given ID.
     */
    Rental getById(Integer id);
}
