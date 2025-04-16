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

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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

    private boolean isPasswordStrong(String password) {
        return password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*\\d.*") &&
                password.matches(".*[@#$%^&+=!].*");
    }


    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(secretKey)
                .compact();
    }


    @Override
    public boolean checkPassword(String rawPassword, String hashedPassword) {
        return BCrypt.checkpw(rawPassword, hashedPassword);
    }
}