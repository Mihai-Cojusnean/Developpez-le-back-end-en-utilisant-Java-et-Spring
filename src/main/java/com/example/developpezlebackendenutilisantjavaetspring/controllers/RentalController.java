package com.example.developpezlebackendenutilisantjavaetspring.controllers;

import com.example.developpezlebackendenutilisantjavaetspring.dto.RentalSaveDTO;
import com.example.developpezlebackendenutilisantjavaetspring.dto.RentalUpdateDTO;
import com.example.developpezlebackendenutilisantjavaetspring.exceptions.UnauthorizedException;
import com.example.developpezlebackendenutilisantjavaetspring.models.Rental;
import com.example.developpezlebackendenutilisantjavaetspring.responses.MessageResponse;
import com.example.developpezlebackendenutilisantjavaetspring.responses.RentalResponse;
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
                            schema = @Schema(implementation = RentalResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)})
    public ResponseEntity<RentalResponse> getRentals() {
        return ResponseEntity.ok(new RentalResponse(rentalService.getAll()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get rental", description = "Returns a specific rental owned by current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rental retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RentalResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)})
    public ResponseEntity<Rental> getRentalById(@PathVariable("id") int id) {
        return ResponseEntity.ok(rentalService.getById(id));
    }

    @PostMapping("")
    @Operation(summary = "Save rental", description = "Saves a new rental")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rental created !",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RentalResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)})
    public ResponseEntity<MessageResponse> saveRentals(@Valid RentalSaveDTO rentalSaveDTO,
                                                      HttpServletRequest request,
                                                      Authentication authentication) throws IOException {
        rentalService.save(rentalSaveDTO, authentication, request);

        return ResponseEntity.ok(new MessageResponse("Rental created !"));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update rental", description = "Updates rental with specific ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rental updated !",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)})
    public ResponseEntity<?> updateRental(@PathVariable("id") int id,
                                          @Valid RentalUpdateDTO rentalUpdateDTO,
                                          Principal principal) {
        try {
            rentalService.update(id, rentalUpdateDTO, principal);
        } catch (UnauthorizedException ex) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(new MessageResponse("Rental updated !"));
    }
}
