package com.team_2.paceplanner.services;

import com.team_2.paceplanner.entities.StepTracking;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface StepTrackingService {
    StepTracking logSteps(StepTracking stepTracking);

    Optional<StepTracking> getStepsForDate(Long userId, LocalDate date);

    List<StepTracking> getWeeklySteps(Long userId);
}
