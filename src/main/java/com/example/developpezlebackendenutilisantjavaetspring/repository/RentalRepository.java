package com.example.developpezlebackendenutilisantjavaetspring.repository;

import com.example.developpezlebackendenutilisantjavaetspring.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> getAllByOwnerId(Integer id);
    Rental getById(Integer id);
}
