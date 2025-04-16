package com.team_2.paceplanner.controllers;

import com.team_2.paceplanner.entities.GpsTracking;
import com.team_2.paceplanner.services.GpsTrackingService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing GPS tracking functionality.
 * Handles displaying GPS data, live tracking, and recording new activities.
 */
@Controller
@RequestMapping("/gps")
public class GpsTrackingController {
    private final GpsTrackingService service;

    /**
     * Constructs controller with required service dependency.
     *
     * @param service Service for GPS tracking operations
     */
    public GpsTrackingController(GpsTrackingService service) {
        this.service = service;
    }

    /**
     * Displays GPS tracking overview page with recent GPS data.
     * Redirects to login if no user session exists.
     *
     * @param model   Spring MVC model
     * @param session HTTP session for user authentication
     * @return View name for GPS tracking page or login redirect
     */
    @GetMapping
    public String showGpsTracking(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        model.addAttribute("gpsData", service.getRecentGpsData(userId));
        return "gps/tracking";
    }

    /**
     * Displays live GPS tracking interface.
     * Redirects to login if no user session exists.
     *
     * @param model   Spring MVC model
     * @param session HTTP session for user authentication
     * @return View name for live tracking page or login redirect
     */
    @GetMapping("/live")
    public String showLiveTracking(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        return "gps/live-tracking";
    }

    /**
     * Displays form for recording new GPS activity.
     * Pre-populates form with user ID.
     * Redirects to login if no user session exists.
     *
     * @param model   Spring MVC model
     * @param session HTTP session for user authentication
     * @return View name for activity form or login redirect
     */
    @GetMapping("/new")
    public String showNewActivityForm(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        GpsTracking gpsTracking = new GpsTracking();
        gpsTracking.setUserId(userId);
        model.addAttribute("gpsTracking", gpsTracking);
        return "gps/activity-form";
    }

    /**
     * Processes GPS activity form submission.
     * Associates GPS data with logged-in user and saves it.
     *
     * @param gpsTracking GPS tracking data from form submission
     * @param session     HTTP session for user authentication
     * @return Redirect to GPS tracking overview or login page
     */
    @PostMapping("/save")
    public String saveGpsActivity(@ModelAttribute GpsTracking gpsTracking, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        gpsTracking.setUserId(userId);
        service.saveGpsData(gpsTracking);
        return "redirect:/gps";
    }
}