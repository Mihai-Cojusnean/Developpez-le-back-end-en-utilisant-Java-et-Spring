package com.example.developpezlebackendenutilisantjavaetspring.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RentalDTO {
    String name;
    Double surface;
    Double price;
    MultipartFile picture;
    String description;
}
