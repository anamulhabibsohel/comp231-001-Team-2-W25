package com.team_2.paceplanner.exceptiions;

/**
 * Exception thrown when attempting to register a user with an email address
 * that already exists in the system.
 * Extends RuntimeException for unchecked exception handling.
 */
public class DuplicateEmailException extends RuntimeException {

    /**
     * Constructs exception with error message.
     *
     * @param message Description of the duplicate email error
     */
    public DuplicateEmailException(String message) {
        super(message);
    }
}