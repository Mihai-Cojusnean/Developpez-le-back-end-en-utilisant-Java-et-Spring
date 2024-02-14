package com.example.developpezlebackendenutilisantjavaetspring.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageError {
    private String message;
}
