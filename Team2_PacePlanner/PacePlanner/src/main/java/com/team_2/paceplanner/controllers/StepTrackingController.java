package com.team_2.paceplanner.controllers;

import com.team_2.paceplanner.entities.StepTracking;
import com.team_2.paceplanner.services.StepTrackingService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * Controller for managing daily step tracking functionality.
 * Handles displaying step counts and logging new step records.
 */
@Controller
@RequestMapping("/steps")
public class StepTrackingController {
    private final StepTrackingService service;

    /**
     * Constructs controller with required service dependency.
     *
     * @param service Service for step tracking operations
     */
    public StepTrackingController(StepTrackingService service) {
        this.service = service;
    }

    /**
     * Displays step tracking overview page.
     * Shows today's steps and weekly step history.
     * Redirects to login if no user session exists.
     *
     * @param model   Spring MVC model
     * @param session HTTP session for user authentication
     * @return View name for step tracking page or login redirect
     */
    @GetMapping
    public String showStepTracking(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        LocalDate today = LocalDate.now();
        model.addAttribute("todaySteps",
                service.getStepsForDate(userId, today).orElse(new StepTracking()));
        model.addAttribute("weeklySteps", service.getWeeklySteps(userId));
        return "steps/tracking";
    }

    /**
     * Displays form for logging new step count.
     * Pre-populates form with user ID.
     * Redirects to login if no user session exists.
     *
     * @param model   Spring MVC model
     * @param session HTTP session for user authentication
     * @return View name for step log form or login redirect
     */
    @GetMapping("/log")
    public String showStepLogForm(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        StepTracking stepTracking = new StepTracking();
        stepTracking.setUserId(userId);
        model.addAttribute("stepTracking", stepTracking);
        return "steps/log-form";
    }

    /**
     * Processes step count form submission.
     * Associates step record with logged-in user and saves it.
     *
     * @param stepTracking Step tracking data from form submission
     * @param session      HTTP session for user authentication
     * @return Redirect to step tracking overview or login page
     */
    @PostMapping("/log")
    public String logSteps(@ModelAttribute StepTracking stepTracking, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        stepTracking.setUserId(userId);
        service.logSteps(stepTracking);
        return "redirect:/steps";
    }
}