package com.team_2.paceplanner.services;

import com.team_2.paceplanner.entities.User;

import java.util.Optional;

public interface UserService {

    User registerUser(User user);

    Optional<User> findUserByEmail(String email);

    String generateToken(User user);

    boolean checkPassword(String rawPassword, String hashedPassword);

}
