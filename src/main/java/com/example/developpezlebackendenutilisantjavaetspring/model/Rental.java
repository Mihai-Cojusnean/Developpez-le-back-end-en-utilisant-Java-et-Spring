package com.example.developpezlebackendenutilisantjavaetspring.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "rentals", schema = "openclassrooms")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String name;
    Double surface;
    Double price;
    String picture;
    String description;
    LocalDateTime created_at;
    LocalDateTime updated_at;
    Integer ownerId;

    public Rental(String name,
                  Double surface,
                  Double price,
                  String description,
                  LocalDateTime created_at,
                  LocalDateTime updated_at,
                  Integer ownerId,
                  String picture) {
        this.name = name;
        this.surface = surface;
        this.price = price;
        this.description = description;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.ownerId = ownerId;
        this.picture = picture;
    }
}
