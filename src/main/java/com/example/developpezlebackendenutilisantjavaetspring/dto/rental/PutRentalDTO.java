package com.example.developpezlebackendenutilisantjavaetspring.dto.rental;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PutRentalDTO {
    @NotEmpty
    @Size(max = 50)
    String name;

    @NotNull
    Double surface;

    @NotNull
    Double price;

    @NotEmpty
    @Size(max = 2500)
    String description;
}
