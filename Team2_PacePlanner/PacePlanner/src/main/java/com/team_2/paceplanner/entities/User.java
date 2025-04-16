package com.team_2.paceplanner.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Entity representing a user in the system.
 * Stores core user information and authentication details.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    /**
     * Unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * User's full name.
     * Limited to 100 characters.
     */
    @Column(nullable = false, length = 100)
    private String name;

    /**
     * User's email address.
     * Must be unique and limited to 150 characters.
     */
    @Column(nullable = false, unique = true, length = 150)
    private String email;

    /**
     * Hashed version of user's password.
     * Stored securely, never as plaintext.
     */
    @Column(nullable = false)
    private String passwordHash;

    /**
     * Timestamp when the user account was created.
     * Automatically set to current time.
     */
    private LocalDateTime createdAt = LocalDateTime.now();
}