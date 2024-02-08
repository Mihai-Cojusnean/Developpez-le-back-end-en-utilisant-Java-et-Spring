package com.example.developpezlebackendenutilisantjavaetspring.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageDTO {
    String message;
    Integer user_id;
    Integer rental_id;
}
