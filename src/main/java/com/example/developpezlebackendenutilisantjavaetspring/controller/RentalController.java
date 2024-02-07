package com.example.developpezlebackendenutilisantjavaetspring.controller;

import com.example.developpezlebackendenutilisantjavaetspring.dto.RentalDTO;
import com.example.developpezlebackendenutilisantjavaetspring.model.Rental;
import com.example.developpezlebackendenutilisantjavaetspring.response.RentalResponse;
import com.example.developpezlebackendenutilisantjavaetspring.service.RentalService;
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
    public ResponseEntity<RentalResponse> getRentalsByOwnerId(Authentication authentication) {
        return ResponseEntity.ok(rentalService.getAllByOwnerId(authentication));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rental> getRentalById(@PathVariable Integer id) {
        return ResponseEntity.ok(rentalService.getById(id));
    }

    @PostMapping("")
    public ResponseEntity<Rental> saveRentals(Authentication authentication,@Valid RentalDTO rentalDTO) throws IOException {
        return ResponseEntity.ok(rentalService.save(rentalDTO, authentication));
    }
}
