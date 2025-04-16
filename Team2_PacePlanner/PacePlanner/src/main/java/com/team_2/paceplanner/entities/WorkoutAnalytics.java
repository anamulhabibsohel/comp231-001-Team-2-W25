package com.team_2.paceplanner.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Entity representing analytics data for a workout session.
 * Tracks performance metrics including steps, calories, heart rate and duration.
 */
@Entity
@Table(name = "workout_analytics")
@Getter
@Setter
@NoArgsConstructor
public class WorkoutAnalytics {

    /**
     * Unique identifier for the workout analytics record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Reference to the user who performed the workout.
     * Must be a positive number.
     */
    @Column(nullable = false)
    @Min(1)
    private Long userId;

    /**
     * Number of steps taken during the workout.
     * Must be non-negative.
     */
    @Column(nullable = false)
    @Min(0)
    private int steps;

    /**
     * Total calories burned during the workout.
     * Must be non-negative.
     */
    @Column(nullable = false)
    @DecimalMin("0.0")
    private float caloriesBurned;

    /**
     * Average heart rate during the workout.
     * Must be between 40 and 220 BPM.
     */
    @Min(40)
    @Max(220)
    private Integer heartRateAvg;

    /**
     * Duration of the workout in minutes.
     * Must be at least 1 minute.
     */
    @Column(nullable = false)
    @Min(1)
    private int durationMinutes;

    /**
     * Date and time when the workout was performed.
     * Automatically set to current time.
     */
    private LocalDateTime workoutDate = LocalDateTime.now();

    /**
     * Timestamp when the record was created.
     * Automatically set to current time.
     */
    private LocalDateTime createdAt = LocalDateTime.now();
}