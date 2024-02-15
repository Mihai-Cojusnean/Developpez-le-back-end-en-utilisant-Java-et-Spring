package com.example.developpezlebackendenutilisantjavaetspring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("user_id")
    Integer userId;

    @NotNull
    @JsonProperty("rental_id")
    Integer rentalId;
}
