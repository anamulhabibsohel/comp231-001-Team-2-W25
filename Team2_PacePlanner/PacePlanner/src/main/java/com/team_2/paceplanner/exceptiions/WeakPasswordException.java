package com.team_2.paceplanner.exceptiions;

/**
 * Exception thrown when a password does not meet minimum security requirements
 * during user registration or password changes.
 * Extends RuntimeException for unchecked exception handling.
 */
public class WeakPasswordException extends RuntimeException {

    /**
     * Constructs exception with error message.
     *
     * @param message Description of the password security requirements violation
     */
    public WeakPasswordException(String message) {
        super(message);
    }
}