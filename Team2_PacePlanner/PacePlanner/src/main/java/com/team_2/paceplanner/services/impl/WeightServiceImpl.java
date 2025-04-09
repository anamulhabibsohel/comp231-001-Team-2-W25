package com.team_2.paceplanner.services.impl;


import com.team_2.paceplanner.dtos.WeightRecordDTO;
import com.team_2.paceplanner.entities.WeightRecord;
import com.team_2.paceplanner.repositories.WeightRecordRepository;
import com.team_2.paceplanner.services.WeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeightServiceImpl implements WeightService {

    private final WeightRecordRepository repository;

    @Autowired
    public WeightServiceImpl(WeightRecordRepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveWeight(WeightRecordDTO dto) {
        WeightRecord record = new WeightRecord();
        record.setUserId(dto.userId);
        record.setDate(dto.date);
        record.setWeight(dto.weight);

        repository.save(record);
    }
}