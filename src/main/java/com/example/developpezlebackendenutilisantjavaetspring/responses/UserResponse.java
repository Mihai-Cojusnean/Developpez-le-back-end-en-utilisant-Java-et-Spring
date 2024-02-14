package com.example.developpezlebackendenutilisantjavaetspring.responses;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    int id;
    String email;
    String name;
    LocalDateTime created_at;
    LocalDateTime updated_at;
}
