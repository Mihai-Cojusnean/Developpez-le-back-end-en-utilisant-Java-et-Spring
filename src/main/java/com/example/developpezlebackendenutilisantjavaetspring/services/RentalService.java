package com.example.developpezlebackendenutilisantjavaetspring.services;

import com.example.developpezlebackendenutilisantjavaetspring.dto.RentalSaveDTO;
import com.example.developpezlebackendenutilisantjavaetspring.dto.RentalUpdateDTO;
import com.example.developpezlebackendenutilisantjavaetspring.exceptions.UnauthorizedException;
import com.example.developpezlebackendenutilisantjavaetspring.models.Rental;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

public interface RentalService {
    /**
     * Saves a rental.
     *
     * @param rentalSaveDTO  The rental data transfer object.
     * @param authentication The user's authentication context.
     * @throws IOException If there are issues related to input/output operations.
     */
    void save(RentalSaveDTO rentalSaveDTO,
              Authentication authentication,
              HttpServletRequest request) throws IOException;

    /**
     * Updates a rental with the specified ID.
     *
     * @param id               The ID of the rental to be updated.
     * @param rentalUpdateDTO  The DTO containing the updated rental information.
     * @param principal        Authenticated user details.
     * @throws UnauthorizedException  If the user lacks necessary permissions.
     */
    void update(int id, RentalUpdateDTO rentalUpdateDTO, Principal principal) throws UnauthorizedException;

    /**
     * Retrieves all rentals.
     *
     * @return A list containing all rentals.
     */
    List<Rental> getAll();

    /**
     * Retrieves a specific rental by its unique identifier.
     *
     * @param id  The rental ID.
     * @return  The {@link Rental} corresponding to the given ID.
     */
    Rental getById(Integer id);
}
