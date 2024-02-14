package com.example.developpezlebackendenutilisantjavaetspring.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
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
@Table(name = "users", schema = "openclassrooms")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Email
    @NotEmpty
    @Size(max = 50)
    String email;

    @NotEmpty
    @Size(max = 20)
    String name;

    @NotEmpty
    @Size(max = 120)
    String password;

    @CreationTimestamp
    LocalDateTime created_at;

    @UpdateTimestamp
    LocalDateTime updated_at;
}
