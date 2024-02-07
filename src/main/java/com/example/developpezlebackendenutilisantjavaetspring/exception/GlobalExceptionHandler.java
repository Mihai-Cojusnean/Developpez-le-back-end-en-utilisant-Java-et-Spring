package com.example.developpezlebackendenutilisantjavaetspring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> customValidationErrorHandling(MethodArgumentNotValidException exception) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage("Validation Error");
        errorDetails.setDetails(exception.getBindingResult().getFieldErrors());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
