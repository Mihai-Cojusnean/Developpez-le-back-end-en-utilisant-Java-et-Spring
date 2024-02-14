package com.example.developpezlebackendenutilisantjavaetspring.controllers;

import com.example.developpezlebackendenutilisantjavaetspring.dto.RentalDTO;
import com.example.developpezlebackendenutilisantjavaetspring.models.Rental;
import com.example.developpezlebackendenutilisantjavaetspring.responses.RentalResponse;
import com.example.developpezlebackendenutilisantjavaetspring.services.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {
    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping("")
    @Operation(summary = "Get rentals", description = "Returns a list of rentals owned by current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of rentals retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RentalResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)})
    public ResponseEntity<RentalResponse> getRentalsByOwnerId(Authentication authentication) {
        return ResponseEntity.ok(rentalService.getAllByOwnerId(authentication));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get rental", description = "Returns a specific rental owned by current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rental retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RentalResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)})
    public ResponseEntity<Rental> getRentalById(@PathVariable Integer id) {
        return ResponseEntity.ok(rentalService.getById(id));
    }

    @PostMapping("")
    @Operation(summary = "Save rental", description = "Saves a new rental")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rental saved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RentalResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)})
    public ResponseEntity<Rental> saveRentals(Authentication authentication, @Valid RentalDTO rentalDTO) throws IOException {
        return ResponseEntity.ok(rentalService.save(rentalDTO, authentication));
    }
}
