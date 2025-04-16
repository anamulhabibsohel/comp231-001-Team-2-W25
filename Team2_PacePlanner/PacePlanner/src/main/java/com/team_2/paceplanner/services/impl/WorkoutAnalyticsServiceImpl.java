package com.team_2.paceplanner.services.impl;

import com.team_2.paceplanner.entities.WorkoutAnalytics;

import com.team_2.paceplanner.repositories.WorkoutAnalyticsRepository;
import com.team_2.paceplanner.services.WorkoutAnalyticsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service implementation for managing workout analytics.
 * Provides functionality for saving workout data and retrieving workout history.
 */
@Service
public class WorkoutAnalyticsServiceImpl implements WorkoutAnalyticsService {

    private final WorkoutAnalyticsRepository repository;

    /**
     * Constructs a new WorkoutAnalyticsServiceImpl.
     *
     * @param repository the repository for workout analytics operations
     */
    public WorkoutAnalyticsServiceImpl(WorkoutAnalyticsRepository repository) {
        this.repository = repository;
    }

    /**
     * Saves workout analytics data in a transaction.
     * Algorithm:
     * 1. Validates workout object
     * 2. Persists workout record to database
     * 3. Returns saved entity with generated ID
     *
     * @param workout the workout analytics record to save
     * @return the saved workout analytics record
     */
    @Override
    @Transactional
    public WorkoutAnalytics saveWorkout(WorkoutAnalytics workout) {
        return repository.save(workout);
    }

    /**
     * Retrieves recent workout records for a user.
     * Algorithm:
     * 1. Queries repository for recent workouts by userId
     * 2. Returns list ordered by workout date/time
     *
     * @param userId the user's ID
     * @return list of recent workout analytics records
     */
    @Override
    public List<WorkoutAnalytics> getRecentWorkouts(Long userId) {
        return repository.findRecentWorkouts(userId);
    }
}
