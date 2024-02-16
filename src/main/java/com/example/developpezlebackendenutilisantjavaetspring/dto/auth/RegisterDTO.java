package com.example.developpezlebackendenutilisantjavaetspring.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterDTO {

    @NotEmpty
    @Email(message = "Email should be valid")
    String email;

    @NotEmpty(message = "Password cannot be null")
    @Size(min = 8, message = "Password should have at least 8 characters")
    String password;

    @NotNull(message = "Name cannot be null")
    String name;
}
