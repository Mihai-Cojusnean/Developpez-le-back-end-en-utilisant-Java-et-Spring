package com.example.developpezlebackendenutilisantjavaetspring.repositories;

import com.example.developpezlebackendenutilisantjavaetspring.models.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    Optional<Rental> getById(Integer id);
}
