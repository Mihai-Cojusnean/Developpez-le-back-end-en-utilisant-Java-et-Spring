package com.example.developpezlebackendenutilisantjavaetspring.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "messages", schema = "openclassrooms")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NotNull
    @JsonProperty("user_id")
    Integer userId;

    @NotNull
    @JsonProperty("rental_id")
    Integer rentalId;

    @NotEmpty
    String message;

    @CreationTimestamp
    @JsonProperty("created_at")
    LocalDateTime createdAt;

    @UpdateTimestamp
    @JsonProperty("updated_at")
    LocalDateTime updatedAt;

    public Message(Integer userId, Integer rentalId, String message) {
        this.userId = userId;
        this.rentalId = rentalId;
        this.message = message;
    }
}
