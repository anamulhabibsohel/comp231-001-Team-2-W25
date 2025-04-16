package com.team_2.paceplanner.services;

import com.team_2.paceplanner.dtos.WeightRecordDTO;

import java.util.List;

public interface WeightService {
    void saveWeight(WeightRecordDTO dto);

    List<WeightRecordDTO> getUserWeightHistory(Long userId);
}
