package com.team_2.paceplanner.repositories;
import com.team_2.paceplanner.entities.StepTracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface StepTrackingRepository extends JpaRepository<StepTracking, Long> {
    @Query("SELECT s FROM StepTracking s WHERE s.userId = :userId AND s.trackingDate = :trackingDate")
    Optional<StepTracking> findByUserIdAndTrackingDate(Long userId, LocalDate trackingDate);
}
