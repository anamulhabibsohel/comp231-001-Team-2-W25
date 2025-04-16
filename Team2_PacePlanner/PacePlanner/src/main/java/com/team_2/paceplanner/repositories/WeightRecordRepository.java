package com.team_2.paceplanner.repositories;

import com.team_2.paceplanner.entities.WeightRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeightRecordRepository extends JpaRepository<WeightRecord, Long> {

    List<WeightRecord> findByUserId(Long userId);
}
