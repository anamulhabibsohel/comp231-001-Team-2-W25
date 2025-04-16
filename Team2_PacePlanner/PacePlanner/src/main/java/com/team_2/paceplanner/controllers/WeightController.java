package com.team_2.paceplanner.controllers;

import com.team_2.paceplanner.dtos.WeightRecordDTO;
import com.team_2.paceplanner.services.WeightService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing weight tracking functionality.
 * Handles displaying weight history and logging new weight records.
 */
@Controller
@RequestMapping("/weight")
public class WeightController {
    private final WeightService service;

    /**
     * Constructs controller with required service dependency.
     *
     * @param service Service for weight tracking operations
     */
    public WeightController(WeightService service) {
        this.service = service;
    }

    /**
     * Displays weight tracking page with user's weight history.
     * Adds empty weight record form for new entries.
     * Redirects to login if no user session exists.
     *
     * @param model   Spring MVC model
     * @param session HTTP session for user authentication
     * @return View name for weight tracking page or login redirect
     */
    @GetMapping
    public String showWeightTracking(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        model.addAttribute("weightRecords", service.getUserWeightHistory(userId));

        WeightRecordDTO weightRecord = new WeightRecordDTO();
        weightRecord.setUserId(userId);
        model.addAttribute("weightRecord", weightRecord);

        return "weight/tracking";
    }

    /**
     * Processes weight record submission.
     * Associates weight record with logged-in user and saves it.
     *
     * @param dto     Weight record data from form submission
     * @param session HTTP session for user authentication
     * @return Redirect to weight tracking page or login page
     */
    @PostMapping("/log")
    public String logWeight(@ModelAttribute WeightRecordDTO dto, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        dto.setUserId(userId);
        service.saveWeight(dto);
        return "redirect:/weight";
    }
}