package com.example.developpezlebackendenutilisantjavaetspring.controllers;

import com.example.developpezlebackendenutilisantjavaetspring.dto.rental.SaveRentalDTO;
import com.example.developpezlebackendenutilisantjavaetspring.dto.rental.PutRentalDTO;
import com.example.developpezlebackendenutilisantjavaetspring.exceptions.ResourceNotFoundException;
import com.example.developpezlebackendenutilisantjavaetspring.exceptions.UnauthorizedException;
import com.example.developpezlebackendenutilisantjavaetspring.responses.MessageResponse;
import com.example.developpezlebackendenutilisantjavaetspring.dto.rental.RentalDTO;
import com.example.developpezlebackendenutilisantjavaetspring.responses.RentalsResponse;
import com.example.developpezlebackendenutilisantjavaetspring.services.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {
    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping("")
    @Operation(summary = "Get rentals", description = "Returns a list of rentals")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of rentals retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RentalsResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)})
    public ResponseEntity<RentalsResponse> getRentals() {
        return ResponseEntity.ok(rentalService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get rental", description = "Returns a specific rental owned by current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rental retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RentalsResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Rental not found", content = @Content)})
    public ResponseEntity<RentalDTO> getRentalById(@PathVariable("id") int id) {
        try {
            RentalDTO rentalDTO = rentalService.getById(id);
            return ResponseEntity.ok(rentalDTO);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("")
    @Operation(summary = "Save rental", description = "Saves a new rental")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rental created !",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RentalsResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)})
    public ResponseEntity<MessageResponse> saveRentals(@Valid SaveRentalDTO saveRentalDTO,
                                                       HttpServletRequest request,
                                                       Authentication authentication) throws IOException {
        rentalService.save(saveRentalDTO, authentication, request);

        return ResponseEntity.ok(new MessageResponse("Rental created !"));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update rental", description = "Updates rental with specific ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rental updated !",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Rental not found", content = @Content)})
    public ResponseEntity<MessageResponse> updateRental(@PathVariable("id") int id,
                                          @Valid PutRentalDTO putRentalDTO,
                                          Principal principal) {
        try {
            rentalService.update(id, putRentalDTO, principal);
        } catch (UnauthorizedException ex) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new MessageResponse("Rental updated !"));
    }
}
