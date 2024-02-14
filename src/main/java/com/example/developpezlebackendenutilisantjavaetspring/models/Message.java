package com.example.developpezlebackendenutilisantjavaetspring.models;

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
    Integer user_id;

    @NotNull
    Integer rental_id;

    @NotEmpty
    String message;

    @CreationTimestamp
    LocalDateTime created_at;

    @UpdateTimestamp
    LocalDateTime updated_at;

    public Message(String message) {
        this.message = message;
    }
}
