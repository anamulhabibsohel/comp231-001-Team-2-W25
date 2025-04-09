package com.team_2.paceplanner.controllers;

import com.team_2.paceplanner.dtos.ActivityRecordDTO;
import com.team_2.paceplanner.services.ActivityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {

    private final ActivityService service;

    public ActivityController(ActivityService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> logActivity(@RequestBody ActivityRecordDTO dto) {
        service.saveActivity(dto);
        return ResponseEntity.ok("Activity logged");
    }
}
