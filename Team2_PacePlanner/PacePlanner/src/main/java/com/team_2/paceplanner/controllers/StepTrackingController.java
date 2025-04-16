package com.team_2.paceplanner.controllers;

import com.team_2.paceplanner.entities.StepTracking;
import com.team_2.paceplanner.services.StepTrackingService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/steps")
public class StepTrackingController {
    private final StepTrackingService service;

    public StepTrackingController(StepTrackingService service) {
        this.service = service;
    }

    @GetMapping
    public String showStepTracking(Model model, HttpSession session) {
        // Get userId from the session
        Long userId = (Long) session.getAttribute("userId");

        // Redirect to login if no user is in session
        if (userId == null) {
            return "redirect:/login";
        }

        LocalDate today = LocalDate.now();

        model.addAttribute("todaySteps",
                service.getStepsForDate(userId, today).orElse(new StepTracking()));
        model.addAttribute("weeklySteps", service.getWeeklySteps(userId));
        return "steps/tracking";
    }

    @GetMapping("/log")
    public String showStepLogForm(Model model, HttpSession session) {
        // Check if user is logged in
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        StepTracking stepTracking = new StepTracking();
        stepTracking.setUserId(userId);  // Pre-populate with the user's ID
        model.addAttribute("stepTracking", stepTracking);
        return "steps/log-form";
    }

    @PostMapping("/log")
    public String logSteps(@ModelAttribute StepTracking stepTracking, HttpSession session) {
        // Check if user is logged in
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        // Ensure the step tracking is associated with the logged-in user
        stepTracking.setUserId(userId);
        service.logSteps(stepTracking);
        return "redirect:/steps";
    }
}