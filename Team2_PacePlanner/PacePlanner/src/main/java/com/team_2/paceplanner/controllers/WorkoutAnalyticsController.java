package com.team_2.paceplanner.controllers;

import com.team_2.paceplanner.entities.WorkoutAnalytics;
import com.team_2.paceplanner.services.WorkoutAnalyticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workouts")
public class WorkoutAnalyticsController {
    private final WorkoutAnalyticsService service;

    public WorkoutAnalyticsController(WorkoutAnalyticsService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<WorkoutAnalytics> logWorkout(@RequestBody WorkoutAnalytics workout) {
        return ResponseEntity.ok(service.saveWorkout(workout));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<WorkoutAnalytics>> getUserWorkouts(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getRecentWorkouts(userId));
    }
}
