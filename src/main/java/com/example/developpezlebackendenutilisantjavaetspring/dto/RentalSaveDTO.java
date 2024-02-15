package com.example.developpezlebackendenutilisantjavaetspring.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RentalSaveDTO {
    @NotEmpty
    @Size(max = 50)
    String name;

    @NotNull
    Double surface;

    @NotNull
    Double price;

    @NotNull
    MultipartFile picture;

    @NotEmpty
    @Size(max = 2500)
    String description;
}
