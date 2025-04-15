package com.team_2.paceplanner.services.impl;

import com.team_2.paceplanner.dtos.WeightRecordDTO;
import com.team_2.paceplanner.entities.WeightRecord;
import com.team_2.paceplanner.repositories.WeightRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WeightServiceImplTest {

    private WeightRecordRepository repository;
    private WeightServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = mock(WeightRecordRepository.class);
        service = new WeightServiceImpl(repository);
    }

    @Test
    @DisplayName("saveWeight should save valid weight record")
    void saveWeightShouldSaveValidWeightRecord() {
        WeightRecordDTO dto = new WeightRecordDTO(1L, LocalDate.now(), 70.5);
        service.saveWeight(dto);

        ArgumentCaptor<WeightRecord> captor = ArgumentCaptor.forClass(WeightRecord.class);
        verify(repository).save(captor.capture());

        WeightRecord savedRecord = captor.getValue();
        assertEquals(dto.userId, savedRecord.getUserId());
        assertEquals(dto.date, savedRecord.getDate());
        assertEquals(dto.weight, savedRecord.getWeight());
    }

    @Test
    @DisplayName("saveWeight should handle null date")
    void saveWeightShouldHandleNullDate() {
        WeightRecordDTO dto = new WeightRecordDTO(1L, null, 70.5);
        service.saveWeight(dto);

        ArgumentCaptor<WeightRecord> captor = ArgumentCaptor.forClass(WeightRecord.class);
        verify(repository).save(captor.capture());

        WeightRecord savedRecord = captor.getValue();
        assertNull(savedRecord.getDate());
    }

    @Test
    @DisplayName("saveWeight should handle zero weight")
    void saveWeightShouldHandleZeroWeight() {
        WeightRecordDTO dto = new WeightRecordDTO(1L, LocalDate.now(), 0.0);
        service.saveWeight(dto);

        ArgumentCaptor<WeightRecord> captor = ArgumentCaptor.forClass(WeightRecord.class);
        verify(repository).save(captor.capture());

        WeightRecord savedRecord = captor.getValue();
        assertEquals(0.0, savedRecord.getWeight());
    }

    @Test
    @DisplayName("saveWeight should handle negative weight")
    void saveWeightShouldHandleNegativeWeight() {
        WeightRecordDTO dto = new WeightRecordDTO(1L, LocalDate.now(), -5.0);
        service.saveWeight(dto);

        ArgumentCaptor<WeightRecord> captor = ArgumentCaptor.forClass(WeightRecord.class);
        verify(repository).save(captor.capture());

        WeightRecord savedRecord = captor.getValue();
        assertEquals(-5.0, savedRecord.getWeight());
    }
}