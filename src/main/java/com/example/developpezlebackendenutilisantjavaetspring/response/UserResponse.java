package com.example.developpezlebackendenutilisantjavaetspring.response;

import java.time.LocalDateTime;

public record UserResponse(int id, String email, String name, LocalDateTime created_at, LocalDateTime updated_at) {
}
