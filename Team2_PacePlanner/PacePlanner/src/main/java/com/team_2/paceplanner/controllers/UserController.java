package com.team_2.paceplanner.controllers;

import com.team_2.paceplanner.entities.User;
import com.team_2.paceplanner.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controller handling user authentication and account management.
 * Provides endpoints for login, registration, dashboard access and logout.
 */
@Controller
public class UserController {

    private final UserService userService;

    /**
     * Constructs controller with required service dependency.
     *
     * @param userService Service for user management operations
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Displays login page with empty user form.
     *
     * @param model Spring MVC model
     * @return View name for login page
     */
    @GetMapping("/login")
    public String showLogin(Model model) {
        model.addAttribute("user", new User());
        return "auth/login";
    }

    /**
     * Processes login form submission.
     * Authenticates user and creates session if credentials are valid.
     *
     * @param user    User credentials from form
     * @param model   Spring MVC model
     * @param session HTTP session for auth storage
     * @return Redirect to dashboard or back to login with error
     */
    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user, Model model, HttpSession session) {
        Optional<User> existingUser = userService.findUserByEmail(user.getEmail());
        if (existingUser.isPresent() && userService.checkPassword(user.getPasswordHash(), existingUser.get().getPasswordHash())) {
            session.setAttribute("userId", existingUser.get().getId());
            return "redirect:/dashboard";
        }
        model.addAttribute("error", "Invalid credentials");
        return "auth/login";
    }

    /**
     * Displays registration page with empty user form.
     *
     * @param model Spring MVC model
     * @return View name for registration page
     */
    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    /**
     * Processes registration form submission.
     * Creates new user account if validation passes.
     *
     * @param user  User data from registration form
     * @param model Spring MVC model
     * @return Redirect to login or back to register with error
     */
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        try {
            userService.registerUser(user);
            return "redirect:/login?registered=true";
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            return "auth/register";
        }
    }

    /**
     * Displays user dashboard page.
     * Shows overview statistics and user data.
     *
     * @param model Spring MVC model
     * @return View name for dashboard page
     */
    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        return "dashboard";
    }

    /**
     * Handles user logout.
     * Invalidates current session.
     *
     * @param session HTTP session to invalidate
     * @return Redirect to login page
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}