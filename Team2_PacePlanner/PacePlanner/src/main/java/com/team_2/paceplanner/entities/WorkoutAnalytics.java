package com.team_2.paceplanner.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "workout_analytics")
@Getter
@Setter
@NoArgsConstructor
public class WorkoutAnalytics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Min(1)
    private Long userId;

    @Column(nullable = false)
    @Min(0)
    private int steps;

    @Column(nullable = false)
    @DecimalMin("0.0")
    private float caloriesBurned;

    @Min(40)
    @Max(220)
    private Integer heartRateAvg;

    @Column(nullable = false)
    @Min(1)
    private int durationMinutes;

    private LocalDateTime workoutDate = LocalDateTime.now();
    private LocalDateTime createdAt = LocalDateTime.now();
}
