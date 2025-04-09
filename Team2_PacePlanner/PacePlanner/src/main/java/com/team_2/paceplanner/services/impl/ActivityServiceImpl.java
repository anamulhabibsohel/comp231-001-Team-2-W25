package com.team_2.paceplanner.services.impl;

import com.team_2.paceplanner.dtos.ActivityRecordDTO;
import com.team_2.paceplanner.entities.ActivityRecord;
import com.team_2.paceplanner.repositories.ActivityRecordRepository;
import com.team_2.paceplanner.services.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRecordRepository repository;

    @Autowired
    public ActivityServiceImpl(ActivityRecordRepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveActivity(ActivityRecordDTO dto) {
        ActivityRecord record = new ActivityRecord();
        record.setUserId(dto.userId);
        record.setSteps(dto.steps);
        record.setDistance(dto.distance);
        record.setCaloriesBurned((int) (dto.steps * 0.04));
        record.setTimestamp(dto.timestamp);

        repository.save(record);
    }
}
