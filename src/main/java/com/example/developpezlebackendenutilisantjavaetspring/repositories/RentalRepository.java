package com.example.developpezlebackendenutilisantjavaetspring.repositories;

import com.example.developpezlebackendenutilisantjavaetspring.models.Rental;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RentalRepository extends JpaRepository<Rental, Integer> {
    @NonNull
    Optional<Rental> findById(@NonNull Integer id);
}
