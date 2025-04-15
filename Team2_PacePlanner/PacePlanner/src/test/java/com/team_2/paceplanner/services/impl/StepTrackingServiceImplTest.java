package com.team_2.paceplanner.services.impl;

import com.team_2.paceplanner.entities.StepTracking;
import com.team_2.paceplanner.repositories.StepTrackingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StepTrackingServiceImplTest {

    private StepTrackingRepository repository;
    private StepTrackingServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = mock(StepTrackingRepository.class);
        service = new StepTrackingServiceImpl(repository);
    }

    @Test
    @DisplayName("logSteps should save valid step tracking record")
    void logStepsShouldSaveValidStepTrackingRecord() {
        StepTracking stepTracking = new StepTracking();
        stepTracking.setUserId(1L);
        stepTracking.setTrackingDate(LocalDate.now());
        stepTracking.setSteps(1000);

        service.logSteps(stepTracking);

        ArgumentCaptor<StepTracking> captor = ArgumentCaptor.forClass(StepTracking.class);
        verify(repository).save(captor.capture());

        StepTracking savedRecord = captor.getValue();
        assertEquals(stepTracking.getUserId(), savedRecord.getUserId());
        assertEquals(stepTracking.getTrackingDate(), savedRecord.getTrackingDate());
        assertEquals(stepTracking.getSteps(), savedRecord.getSteps());
    }

    @Test
    @DisplayName("getStepsForDate should return step tracking record for valid user and date")
    void getStepsForDateShouldReturnStepTrackingRecordForValidUserAndDate() {
        StepTracking stepTracking = new StepTracking();
        stepTracking.setUserId(1L);
        stepTracking.setTrackingDate(LocalDate.now());
        stepTracking.setSteps(1000);

        when(repository.findByUserIdAndTrackingDate(1L, LocalDate.now())).thenReturn(Optional.of(stepTracking));

        Optional<StepTracking> result = service.getStepsForDate(1L, LocalDate.now());

        assertTrue(result.isPresent());
        assertEquals(stepTracking, result.get());
    }

    @Test
    @DisplayName("getStepsForDate should return empty optional for non-existent user or date")
    void getStepsForDateShouldReturnEmptyOptionalForNonExistentUserOrDate() {
        when(repository.findByUserIdAndTrackingDate(1L, LocalDate.now())).thenReturn(Optional.empty());

        Optional<StepTracking> result = service.getStepsForDate(1L, LocalDate.now());

        assertFalse(result.isPresent());
    }
}