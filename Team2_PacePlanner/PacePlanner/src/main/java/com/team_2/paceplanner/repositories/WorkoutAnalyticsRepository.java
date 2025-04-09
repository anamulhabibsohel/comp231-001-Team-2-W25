package com.team_2.paceplanner.repositories;


import com.team_2.paceplanner.entities.WorkoutAnalytics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutAnalyticsRepository extends JpaRepository<WorkoutAnalytics, Long> {

    @Query("SELECT w FROM WorkoutAnalytics w WHERE w.userId = :userId ORDER BY w.workoutDate DESC")
    List<WorkoutAnalytics> findRecentWorkouts(Long userId);
}
