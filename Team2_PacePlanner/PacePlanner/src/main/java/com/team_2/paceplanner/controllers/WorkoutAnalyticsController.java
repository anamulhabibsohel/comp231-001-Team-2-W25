package com.team_2.paceplanner.controllers;

import com.team_2.paceplanner.entities.WorkoutAnalytics;
import com.team_2.paceplanner.services.WorkoutAnalyticsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controller handling workout analytics management.
 * Provides endpoints for viewing, creating and saving workout records.
 */
@Controller
@RequestMapping("/workouts")
public class WorkoutAnalyticsController {
    private final WorkoutAnalyticsService service;

    /**
     * Constructs controller with required service dependency.
     *
     * @param service Service for workout analytics operations
     */
    public WorkoutAnalyticsController(WorkoutAnalyticsService service) {
        this.service = service;
    }

    /**
     * Displays list of recent workouts for the logged-in user.
     * Redirects to login if no user session exists.
     *
     * @param model   Spring MVC model
     * @param session HTTP session for user authentication
     * @return View name for workout list or login redirect
     */
    @GetMapping
    public String showWorkouts(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        model.addAttribute("workouts", service.getRecentWorkouts(userId));
        return "workout/list";
    }

    /**
     * Displays form for recording a new workout.
     * Redirects to login if no user session exists.
     *
     * @param model   Spring MVC model
     * @param session HTTP session for user authentication
     * @return View name for workout form or login redirect
     */
    @GetMapping("/new")
    public String showNewWorkoutForm(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        model.addAttribute("workout", new WorkoutAnalytics());
        return "workout/form";
    }

    /**
     * Processes workout form submission.
     * Associates workout with logged-in user and saves record.
     *
     * @param workout Workout data from form submission
     * @param session HTTP session for user authentication
     * @return Redirect to workout list or login page
     */
    @PostMapping("/save")
    public String saveWorkout(@ModelAttribute WorkoutAnalytics workout, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        workout.setUserId(userId);
        service.saveWorkout(workout);
        return "redirect:/workouts";
    }
}