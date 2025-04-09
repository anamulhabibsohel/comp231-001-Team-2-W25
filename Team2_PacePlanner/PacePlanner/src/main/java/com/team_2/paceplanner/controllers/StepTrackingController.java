package com.team_2.paceplanner.controllers;

import com.team_2.paceplanner.entities.StepTracking;
import com.team_2.paceplanner.services.StepTrackingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/steps")
public class StepTrackingController {

    private final StepTrackingService service;

    public StepTrackingController(StepTrackingService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<StepTracking> logSteps(@RequestBody StepTracking stepTracking) {
        return ResponseEntity.ok(service.logSteps(stepTracking));
    }

    @GetMapping("/{userId}/{date}")
    public ResponseEntity<StepTracking> getStepsForDate(@PathVariable Long userId, @PathVariable String date) {
        return service.getStepsForDate(userId, LocalDate.parse(date))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
