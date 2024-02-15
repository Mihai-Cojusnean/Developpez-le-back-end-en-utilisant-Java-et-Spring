package com.example.developpezlebackendenutilisantjavaetspring.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @NotEmpty
    @Size(max = 50)
    String name;

    @NotNull
    Double surface;

    @NotNull
    Double price;

    @NotEmpty
    String picture;

    @NotEmpty
    @Size(max = 2500)
    String description;

    @CreationTimestamp
    @JsonProperty("created_at")
    LocalDateTime createdAt;

    @UpdateTimestamp
    @JsonProperty("updated_at")
    LocalDateTime updatedAt;

    @NotNull
    @JsonProperty("owner_id")
    Integer ownerId;
}
