package com.team_2.paceplanner.repositories;

import com.team_2.paceplanner.entities.GpsTracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GpsTrackingRepository extends JpaRepository<GpsTracking, Long> {
    @Query("SELECT g FROM GpsTracking g WHERE g.userId = :userId ORDER BY g.recordedAt DESC")
    List<GpsTracking> findRecentGpsData(Long userId);
}
