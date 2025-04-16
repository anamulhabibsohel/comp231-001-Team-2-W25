package com.team_2.paceplanner.controllers;

import com.team_2.paceplanner.entities.User;
import com.team_2.paceplanner.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Show login page
    @GetMapping("/login")
    public String showLogin(Model model) {
        model.addAttribute("user", new User());
        return "auth/login";
    }

    // Process login
    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user, Model model, HttpSession session) {
        Optional<User> existingUser = userService.findUserByEmail(user.getEmail());
        if (existingUser.isPresent() && userService.checkPassword(user.getPasswordHash(), existingUser.get().getPasswordHash())) {
            // Store user ID in session
            session.setAttribute("userId", existingUser.get().getId());
            return "redirect:/dashboard";
        }
        model.addAttribute("error", "Invalid credentials");
        return "auth/login";
    }

    // Show registration page
    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    // Process registration
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        try {
            User registeredUser = userService.registerUser(user);
            return "redirect:/login?registered=true";
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            return "auth/register";
        }
    }

    // Show dashboard
    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        // Add user data and overview statistics to model
        // This would typically come from the service layer
        return "dashboard";
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}