package com.ds.assignment.coursemanagementservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        if ("Access denied".equals(ex.getMessage())) {
            // Return 403 Forbidden with the custom error message
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied for Role");
        }
        else {
            // For other runtime exceptions, return 500 Internal Server Error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }
}
