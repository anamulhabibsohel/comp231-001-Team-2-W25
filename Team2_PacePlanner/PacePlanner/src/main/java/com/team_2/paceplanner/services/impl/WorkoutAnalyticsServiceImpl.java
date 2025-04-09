package com.team_2.paceplanner.services.impl;

import com.team_2.paceplanner.entities.WorkoutAnalytics;

import com.team_2.paceplanner.repositories.WorkoutAnalyticsRepository;
import com.team_2.paceplanner.services.WorkoutAnalyticsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WorkoutAnalyticsServiceImpl implements WorkoutAnalyticsService {

    private final WorkoutAnalyticsRepository repository;

    public WorkoutAnalyticsServiceImpl(WorkoutAnalyticsRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public WorkoutAnalytics saveWorkout(WorkoutAnalytics workout) {
        return repository.save(workout);
    }

    @Override
    public List<WorkoutAnalytics> getRecentWorkouts(Long userId) {
        return repository.findRecentWorkouts(userId);
    }
}
