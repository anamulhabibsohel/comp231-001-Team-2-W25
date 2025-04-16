package com.team_2.paceplanner.controllers;

import com.team_2.paceplanner.entities.WorkoutAnalytics;
import com.team_2.paceplanner.services.WorkoutAnalyticsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/workouts")
public class WorkoutAnalyticsController {
    private final WorkoutAnalyticsService service;

    public WorkoutAnalyticsController(WorkoutAnalyticsService service) {
        this.service = service;
    }

    @GetMapping
    public String showWorkouts(Model model, HttpSession session) {
        // Get userId from the session
        Long userId = (Long) session.getAttribute("userId");

        // Redirect to login if no user is in session
        if (userId == null) {
            return "redirect:/login";
        }

        model.addAttribute("workouts", service.getRecentWorkouts(userId));
        return "workout/list";
    }

    @GetMapping("/new")
    public String showNewWorkoutForm(Model model, HttpSession session) {
        // Check if user is logged in
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        model.addAttribute("workout", new WorkoutAnalytics());
        return "workout/form";
    }

    @PostMapping("/save")
    public String saveWorkout(@ModelAttribute WorkoutAnalytics workout, HttpSession session) {
        // Check if user is logged in
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        // Set the user ID on the workout before saving
        workout.setUserId(userId);
        service.saveWorkout(workout);
        return "redirect:/workouts";
    }
}