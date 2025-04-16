package com.team_2.paceplanner.exceptiions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for the application.
 * Provides centralized exception handling across all controllers.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles duplicate email exceptions during user registration.
     * Returns 409 Conflict with error message.
     *
     * @param e The caught duplicate email exception
     * @return ResponseEntity with error details and conflict status
     */
    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<?> handleDuplicateEmailException(DuplicateEmailException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("{\"error\": \"" + e.getMessage() + "\"}");
    }

    /**
     * Handles weak password exceptions during user registration.
     * Returns 400 Bad Request with error message.
     *
     * @param e The caught weak password exception
     * @return ResponseEntity with error details and bad request status
     */
    @ExceptionHandler(WeakPasswordException.class)
    public ResponseEntity<?> handleWeakPasswordException(WeakPasswordException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("{\"error\": \"" + e.getMessage() + "\"}");
    }

    /**
     * Handles illegal argument exceptions across the application.
     * Returns 400 Bad Request with error message.
     *
     * @param e The caught illegal argument exception
     * @return ResponseEntity with error details and bad request status
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("{\"error\": \"" + e.getMessage() + "\"}");
    }
}