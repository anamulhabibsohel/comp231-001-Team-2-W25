package com.team_2.paceplanner.services.impl;


import com.team_2.paceplanner.dtos.WeightRecordDTO;
import com.team_2.paceplanner.entities.WeightRecord;
import com.team_2.paceplanner.repositories.WeightRecordRepository;
import com.team_2.paceplanner.services.WeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for managing weight records.
 * Provides functionality for saving and retrieving user weight data.
 */
@Service
public class WeightServiceImpl implements WeightService {

    private final WeightRecordRepository repository;

    /**
     * Constructs a new WeightServiceImpl with the specified repository.
     *
     * @param repository the repository for weight record operations
     */
    @Autowired
    public WeightServiceImpl(WeightRecordRepository repository) {
        this.repository = repository;
    }

    /**
     * Saves a new weight record for a user.
     * Algorithm:
     * 1. Creates new WeightRecord entity
     * 2. Maps DTO fields to entity
     * 3. Persists entity to database
     *
     * @param dto the weight record data transfer object
     */
    @Override
    public void saveWeight(WeightRecordDTO dto) {
        WeightRecord record = new WeightRecord();
        record.setUserId(dto.userId);
        record.setDate(dto.date);
        record.setWeight(dto.weight);

        repository.save(record);
    }

    /**
     * Retrieves the weight history for a specific user.
     * Algorithm:
     * 1. Queries repository for records by userId
     * 2. Streams results and maps each entity to DTO:
     * - Sets userId from record
     * - Copies weight value
     * 3. Collects results to immutable list
     *
     * @param userId the ID of the user
     * @return list of weight records for the user
     */
    @Override
    public List<WeightRecordDTO> getUserWeightHistory(Long userId) {
        return repository.findByUserId(userId).stream()
                .map(record -> {
                    WeightRecordDTO dto = new WeightRecordDTO();
                    dto.setUserId(record.getId());
                    dto.setUserId(record.getUserId());
                    dto.setWeight(record.getWeight());
                    return dto;
                })
                .toList();
    }
}