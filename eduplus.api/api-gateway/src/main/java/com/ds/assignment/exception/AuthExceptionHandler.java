package com.ds.assignment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        if ("missing authorization header".equals(ex.getMessage())) {
            // Return 403 Forbidden with the custom error message
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Missing authorization header");
        }
        if("not a valid token".equals(ex.getMessage())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not a valid token");
        }else {
            // For other runtime exceptions, return 500 Internal Server Error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }
}

