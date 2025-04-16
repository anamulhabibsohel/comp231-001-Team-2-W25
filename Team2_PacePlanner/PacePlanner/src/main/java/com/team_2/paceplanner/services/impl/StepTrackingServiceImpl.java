package com.team_2.paceplanner.services.impl;

import com.team_2.paceplanner.entities.StepTracking;
import com.team_2.paceplanner.repositories.StepTrackingRepository;
import com.team_2.paceplanner.services.StepTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service implementation for managing step tracking records.
 * Provides functionality for logging steps and retrieving step tracking data.
 */
@Service
public class StepTrackingServiceImpl implements StepTrackingService {

    private final StepTrackingRepository repository;

    /**
     * Constructs a new StepTrackingServiceImpl.
     *
     * @param repository the repository for step tracking operations
     */
    @Autowired
    public StepTrackingServiceImpl(StepTrackingRepository repository) {
        this.repository = repository;
    }

    /**
     * Logs a new step tracking record.
     * Algorithm:
     * 1. Validates stepTracking object for null
     * 2. Persists record to database in a transaction
     * 3. Returns saved entity with generated ID
     *
     * @param stepTracking the step tracking record to save
     * @return the saved step tracking record
     * @throws IllegalArgumentException if stepTracking is null
     */
    @Override
    @Transactional
    public StepTracking logSteps(StepTracking stepTracking) {
        return repository.save(stepTracking);
    }

    /**
     * Retrieves step tracking record for a specific user and date.
     * Algorithm:
     * 1. Queries repository with composite condition:
     * - Matches userId
     * - Matches exact tracking date
     * 2. Returns Optional wrapper containing result
     *
     * @param userId the user's ID
     * @param date   the date to get steps for
     * @return an Optional containing the step tracking record if found
     */
    @Override
    public Optional<StepTracking> getStepsForDate(Long userId, LocalDate date) {
        return repository.findByUserIdAndTrackingDate(userId, date);
    }

    /**
     * Gets step tracking records for the current week.
     * Algorithm:
     * 1. Calculates start of week:
     * - Gets current date
     * - Adjusts to Monday of current week
     * 2. Calculates end of week:
     * - Adds 6 days to start date
     * 3. Queries repository for records:
     * - Matches userId
     * - Date falls within week range (inclusive)
     *
     * @param userId the user's ID
     * @return list of step tracking records for the current week
     */
    @Override
    public List<StepTracking> getWeeklySteps(Long userId) {
        LocalDate startOfWeek = LocalDate.now().with(java.time.DayOfWeek.MONDAY);
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        return repository.findByUserIdAndTrackingDateBetween(userId, startOfWeek, endOfWeek);
    }
}