package com.example.developpezlebackendenutilisantjavaetspring.dto.rental;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RentalDTO {
    Integer id;
    String name;
    Double surface;
    Double price;
    String picture;
    String description;
    @JsonProperty("created_at")
    LocalDateTime createdAt;
    @JsonProperty("updated_at")
    LocalDateTime updatedAt;
    @JsonProperty("owner_id")
    Integer ownerId;
}
