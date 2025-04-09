package com.team_2.paceplanner.services.impl;

import com.team_2.paceplanner.entities.StepTracking;
import com.team_2.paceplanner.repositories.StepTrackingRepository;
import com.team_2.paceplanner.services.StepTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class StepTrackingServiceImpl implements StepTrackingService {

    private final StepTrackingRepository repository;

    @Autowired
    public StepTrackingServiceImpl(StepTrackingRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public StepTracking logSteps(StepTracking stepTracking) {
        return repository.save(stepTracking);
    }

    @Override
    public Optional<StepTracking> getStepsForDate(Long userId, LocalDate date) {
        return repository.findByUserIdAndTrackingDate(userId, date);
    }
}
