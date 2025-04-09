package com.team_2.paceplanner.services;

import com.team_2.paceplanner.entities.WorkoutAnalytics;

import java.util.List;


public interface WorkoutAnalyticsService {
    WorkoutAnalytics saveWorkout(WorkoutAnalytics workout);

    List<WorkoutAnalytics> getRecentWorkouts(Long userId);

}
