package com.team_2.paceplanner.services.impl;

import com.team_2.paceplanner.entities.User;
import com.team_2.paceplanner.exceptiions.DuplicateEmailException;
import com.team_2.paceplanner.exceptiions.WeakPasswordException;
import com.team_2.paceplanner.repositories.UserRepository;
import com.team_2.paceplanner.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;

/**
 * Implementation of the UserService interface that handles user-related operations.
 * Provides functionality for user registration, authentication, and token generation.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /**
     * Constructs a new UserServiceImpl with the specified repository.
     *
     * @param userRepository the repository for user operations
     */
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Registers a new user in the system.
     * Algorithm:
     * 1. Validates password strength using regex patterns
     * 2. If password is weak, throws WeakPasswordException
     * 3. Generates salt and hashes password using BCrypt
     * 4. Attempts to save user to database
     * 5. Handles duplicate email conflict by throwing DuplicateEmailException
     *
     * @param user the user to register
     * @return the registered user with generated ID
     * @throws WeakPasswordException   if password doesn't meet strength requirements
     * @throws DuplicateEmailException if email is already registered
     */
    @Override
    public User registerUser(User user) {
        if (!isPasswordStrong(user.getPasswordHash())) {
            throw new WeakPasswordException("Password must be at least 8 characters, contain uppercase, lowercase, a number, and a special character.");
        }
        try {
            user.setPasswordHash(BCrypt.hashpw(user.getPasswordHash(), BCrypt.gensalt()));
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEmailException("Email is already in use");
        }
    }

    /**
     * Validates password strength according to security requirements.
     * Algorithm:
     * 1. Checks minimum length of 8 characters
     * 2. Ensures presence of uppercase letter using regex
     * 3. Ensures presence of lowercase letter using regex
     * 4. Ensures presence of digit using regex
     * 5. Ensures presence of special character using regex
     *
     * @param password the password to validate
     * @return true if password meets all requirements
     */
    private boolean isPasswordStrong(String password) {
        return password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*\\d.*") &&
                password.matches(".*[@#$%^&+=!].*");
    }

    /**
     * Finds a user by their email address.
     * Algorithm:
     * 1. Queries repository with email
     * 2. Returns Optional containing user if found, empty if not
     *
     * @param email the email to search for
     * @return an Optional containing the user if found
     */
    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Generates a JWT token for the specified user.
     * Algorithm:
     * 1. Creates JWT builder
     * 2. Sets subject to user's email
     * 3. Sets issuance time to current timestamp
     * 4. Sets expiration to 24 hours from now
     * 5. Signs token with HS256 algorithm
     * 6. Builds compact token string
     *
     * @param user the user to generate token for
     * @return the generated JWT token
     */
    @Override
    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(secretKey)
                .compact();
    }

    /**
     * Verifies if a raw password matches a hashed password.
     * Algorithm:
     * 1. Uses BCrypt's checkpw method to:
     * - Extract salt from hashed password
     * - Hash raw password with extracted salt
     * - Compare resulting hash with stored hash
     *
     * @param rawPassword    the raw password to check
     * @param hashedPassword the hashed password to compare against
     * @return true if passwords match, false otherwise
     */
    @Override
    public boolean checkPassword(String rawPassword, String hashedPassword) {
        return BCrypt.checkpw(rawPassword, hashedPassword);
    }
}
