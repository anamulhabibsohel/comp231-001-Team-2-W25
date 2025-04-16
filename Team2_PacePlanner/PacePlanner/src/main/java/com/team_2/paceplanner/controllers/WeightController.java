package com.team_2.paceplanner.controllers;

import com.team_2.paceplanner.dtos.WeightRecordDTO;
import com.team_2.paceplanner.services.WeightService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/weight")
public class WeightController {
    private final WeightService service;

    public WeightController(WeightService service) {
        this.service = service;
    }

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