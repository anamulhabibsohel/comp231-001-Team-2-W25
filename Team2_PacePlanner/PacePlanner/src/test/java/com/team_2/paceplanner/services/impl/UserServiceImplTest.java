package com.team_2.paceplanner.services.impl;

import com.team_2.paceplanner.entities.User;
import com.team_2.paceplanner.exceptiions.DuplicateEmailException;
import com.team_2.paceplanner.exceptiions.WeakPasswordException;
import com.team_2.paceplanner.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private UserRepository userRepository;
    private UserServiceImpl service;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        service = new UserServiceImpl(userRepository);
    }

    @Test
    @DisplayName("registerUser should save user with strong password and unique email")
    void registerUserShouldSaveUserWithStrongPasswordAndUniqueEmail() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPasswordHash("StrongP@ss1");

        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        User savedUser = service.registerUser(user);

        assertNotNull(savedUser);
        assertEquals(user.getEmail(), savedUser.getEmail());
        assertTrue(BCrypt.checkpw("StrongP@ss1", savedUser.getPasswordHash()));
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("registerUser should throw WeakPasswordException for password without uppercase")
    void registerUserShouldThrowWeakPasswordExceptionForPasswordWithoutUppercase() {
        User user = new User();
        user.setPasswordHash("weakpass1@");

        assertThrows(WeakPasswordException.class, () -> service.registerUser(user));
        verifyNoInteractions(userRepository);
    }

    @Test
    @DisplayName("registerUser should throw WeakPasswordException for password without lowercase")
    void registerUserShouldThrowWeakPasswordExceptionForPasswordWithoutLowercase() {
        User user = new User();
        user.setPasswordHash("WEAKPASS1@");

        assertThrows(WeakPasswordException.class, () -> service.registerUser(user));
        verifyNoInteractions(userRepository);
    }

    @Test
    @DisplayName("registerUser should throw WeakPasswordException for password without number")
    void registerUserShouldThrowWeakPasswordExceptionForPasswordWithoutNumber() {
        User user = new User();
        user.setPasswordHash("WeakPass@");

        assertThrows(WeakPasswordException.class, () -> service.registerUser(user));
        verifyNoInteractions(userRepository);
    }

    @Test
    @DisplayName("registerUser should throw WeakPasswordException for password without special character")
    void registerUserShouldThrowWeakPasswordExceptionForPasswordWithoutSpecialCharacter() {
        User user = new User();
        user.setPasswordHash("WeakPass1");

        assertThrows(WeakPasswordException.class, () -> service.registerUser(user));
        verifyNoInteractions(userRepository);
    }

    @Test
    @DisplayName("registerUser should throw WeakPasswordException for short password")
    void registerUserShouldThrowWeakPasswordExceptionForShortPassword() {
        User user = new User();
        user.setPasswordHash("Wp1@");

        assertThrows(WeakPasswordException.class, () -> service.registerUser(user));
        verifyNoInteractions(userRepository);
    }

    @Test
    @DisplayName("registerUser should throw DuplicateEmailException for duplicate email")
    void registerUserShouldThrowDuplicateEmailExceptionForDuplicateEmail() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPasswordHash("StrongP@ss1");

        when(userRepository.save(any(User.class))).thenThrow(new DataIntegrityViolationException("Duplicate email"));

        assertThrows(DuplicateEmailException.class, () -> service.registerUser(user));
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("findUserByEmail should return user for valid email")
    void findUserByEmailShouldReturnUserForValidEmail() {
        User user = new User();
        user.setEmail("test@example.com");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        Optional<User> result = service.findUserByEmail("test@example.com");

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    @DisplayName("findUserByEmail should return empty optional for non-existent email")
    void findUserByEmailShouldReturnEmptyOptionalForNonExistentEmail() {
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        Optional<User> result = service.findUserByEmail("nonexistent@example.com");

        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("generateToken should return valid JWT token for user")
    void generateTokenShouldReturnValidTokenForUser() {
        User user = new User();
        user.setEmail("test@example.com");

        String token = service.generateToken(user);

        assertNotNull(token);
        assertTrue(token.split("\\.").length == 3); // Verify JWT format (header.payload.signature)
    }
}