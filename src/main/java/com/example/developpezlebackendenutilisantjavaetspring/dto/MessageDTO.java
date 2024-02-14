package com.example.developpezlebackendenutilisantjavaetspring.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageDTO {
    @NotEmpty(message = "Message cannot be null")
    String message;

    @NotNull
    Integer user_id;

    @NotNull
    Integer rental_id;
}
