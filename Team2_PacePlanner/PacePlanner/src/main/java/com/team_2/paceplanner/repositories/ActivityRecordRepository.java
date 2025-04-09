package com.team_2.paceplanner.repositories;

import com.team_2.paceplanner.entities.ActivityRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRecordRepository extends JpaRepository<ActivityRecord, Long> {
}
