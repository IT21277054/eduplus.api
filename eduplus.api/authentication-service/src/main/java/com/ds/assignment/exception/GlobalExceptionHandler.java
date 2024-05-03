package com.ds.assignment.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @Autowired
    private AuthenticationManager authenticationManager;
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        if ("missing authorization header".equals(ex.getMessage())) {
            // Return 403 Forbidden with the custom error message
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Missing authorization header");
        }
        if("not a valid token".equals(ex.getMessage())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not a valid token");
        }
        if("Invalid access".equals(ex.getMessage())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid access details");
        }
        if("User registration failed".equals(ex.getMessage())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User registration failed");
        }
        else {
            // For other runtime exceptions, return 500 Internal Server Error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }
    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<String> handleUserExistsException(UserExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException ex) {
        if (ex instanceof BadCredentialsException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        } else if (ex instanceof LockedException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Your account is locked");
        } else if (ex instanceof AuthenticationServiceException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication service exception");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed: " + ex.getMessage());
        }
    }
}

