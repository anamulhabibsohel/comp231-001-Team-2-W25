package com.team_2.paceplanner.controllers;

import com.team_2.paceplanner.entities.GpsTracking;
import com.team_2.paceplanner.services.GpsTrackingService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/gps")
public class GpsTrackingController {
    private final GpsTrackingService service;

    public GpsTrackingController(GpsTrackingService service) {
        this.service = service;
    }

    @GetMapping
    public String showGpsTracking(Model model, HttpSession session) {
        // Get userId from the session
        Long userId = (Long) session.getAttribute("userId");

        // Redirect to login if no user is in session
        if (userId == null) {
            return "redirect:/login";
        }

        model.addAttribute("gpsData", service.getRecentGpsData(userId));
        return "gps/tracking";
    }

    @GetMapping("/live")
    public String showLiveTracking(Model model, HttpSession session) {
        // Get userId from the session
        Long userId = (Long) session.getAttribute("userId");

        // Redirect to login if no user is in session
        if (userId == null) {
            return "redirect:/login";
        }

        return "gps/live-tracking";
    }

    @GetMapping("/new")
    public String showNewActivityForm(Model model, HttpSession session) {
        // Get userId from the session
        Long userId = (Long) session.getAttribute("userId");

        // Redirect to login if no user is in session
        if (userId == null) {
            return "redirect:/login";
        }

        GpsTracking gpsTracking = new GpsTracking();
        gpsTracking.setUserId(userId);  // Pre-populate with the user's ID
        model.addAttribute("gpsTracking", gpsTracking);
        return "gps/activity-form";
    }

    @PostMapping("/save")
    public String saveGpsActivity(@ModelAttribute GpsTracking gpsTracking, HttpSession session) {
        // Get userId from the session
        Long userId = (Long) session.getAttribute("userId");

        // Redirect to login if no user is in session
        if (userId == null) {
            return "redirect:/login";
        }

        // Ensure the GPS tracking is associated with the logged-in user
        gpsTracking.setUserId(userId);
        service.saveGpsData(gpsTracking);
        return "redirect:/gps";
    }
}