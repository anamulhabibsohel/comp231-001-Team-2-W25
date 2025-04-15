package com.team_2.paceplanner.services.impl;

import com.team_2.paceplanner.dtos.ActivityRecordDTO;
import com.team_2.paceplanner.entities.ActivityRecord;
import com.team_2.paceplanner.repositories.ActivityRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ActivityServiceImplTest {

    private ActivityRecordRepository repository;
    private ActivityServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = mock(ActivityRecordRepository.class);
        service = new ActivityServiceImpl(repository);
    }

    @Test
    @DisplayName("saveActivity should save valid activity record")
    void saveActivityShouldSaveValidActivityRecord() {
        ActivityRecordDTO dto = new ActivityRecordDTO(1L, 1000, 0.8, LocalDateTime.now());
        service.saveActivity(dto);

        ArgumentCaptor<ActivityRecord> captor = ArgumentCaptor.forClass(ActivityRecord.class);
        verify(repository).save(captor.capture());

        ActivityRecord savedRecord = captor.getValue();
        assertEquals(dto.userId, savedRecord.getUserId());
        assertEquals(dto.steps, savedRecord.getSteps());
        assertEquals(dto.distance, savedRecord.getDistance());
        assertEquals((int) (dto.steps * 0.04), savedRecord.getCaloriesBurned());
        assertEquals(dto.timestamp, savedRecord.getTimestamp());
    }

    @Test
    @DisplayName("saveActivity should handle zero steps")
    void saveActivityShouldHandleZeroSteps() {
        ActivityRecordDTO dto = new ActivityRecordDTO(1L, 0, 0.0, LocalDateTime.now());
        service.saveActivity(dto);

        ArgumentCaptor<ActivityRecord> captor = ArgumentCaptor.forClass(ActivityRecord.class);
        verify(repository).save(captor.capture());

        ActivityRecord savedRecord = captor.getValue();
        assertEquals(0, savedRecord.getSteps());
        assertEquals(0, savedRecord.getCaloriesBurned());
    }

    @Test
    @DisplayName("saveActivity should handle null timestamp")
    void saveActivityShouldHandleNullTimestamp() {
        ActivityRecordDTO dto = new ActivityRecordDTO(1L, 500, 0.4, null);
        service.saveActivity(dto);

        ArgumentCaptor<ActivityRecord> captor = ArgumentCaptor.forClass(ActivityRecord.class);
        verify(repository).save(captor.capture());

        ActivityRecord savedRecord = captor.getValue();
        assertNull(savedRecord.getTimestamp());
    }
}