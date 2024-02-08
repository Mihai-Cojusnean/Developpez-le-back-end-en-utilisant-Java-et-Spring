package com.example.developpezlebackendenutilisantjavaetspring.response;

import com.example.developpezlebackendenutilisantjavaetspring.model.Rental;

import java.util.List;

public record RentalResponse(List<Rental> rentals) {
}
