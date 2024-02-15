package com.example.developpezlebackendenutilisantjavaetspring.responses;

import com.example.developpezlebackendenutilisantjavaetspring.models.Rental;

import java.util.List;

public record RentalResponse(List<Rental> rentals) {
}
