package com.example.developpezlebackendenutilisantjavaetspring.exception;

import lombok.Data;
import org.springframework.validation.FieldError;

import java.util.List;

@Data
public class ErrorDetails {
    private String message;
    private List<FieldError> details;
}
