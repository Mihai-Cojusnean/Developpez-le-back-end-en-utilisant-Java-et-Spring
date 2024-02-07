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
@Table(name = "messages", schema = "openclassrooms")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    Integer user_id;
    Integer rental_id;
    String message;
    LocalDateTime created_at;
    LocalDateTime updated_at;

    public Message(Integer user_id,
                   Integer rental_id,
                   String message,
                   LocalDateTime created_at,
                   LocalDateTime updated_at) {
        this.user_id = user_id;
        this.rental_id = rental_id;
        this.message = message;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Message(String message) {
        this.message = message;
    }
}
