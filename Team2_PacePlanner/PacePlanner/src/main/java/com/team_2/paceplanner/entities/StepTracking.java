package com.team_2.paceplanner.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entity representing daily step tracking records.
 * Stores step counts and tracking information for user activities.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "step_tracking")
public class StepTracking {

    /**
     * Unique identifier for the step tracking record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Reference to the user whose steps are being tracked.
     */
    @Column(nullable = false)
    private Long userId;

    /**
     * Number of steps recorded.
     * Must be non-negative.
     */
    @Column(nullable = false)
    @Min(0)
    private int steps;

    /**
     * Date when the steps were tracked.
     */
    @Column(nullable = false)
    private LocalDate trackingDate;

    /**
     * Optional notes about the day's activity.
     * Limited to 500 characters.
     */
    @Column(length = 500)
    private String notes;

    /**
     * Timestamp when the record was created.
     * Automatically set to current time.
     */
    private LocalDateTime createdAt = LocalDateTime.now();
}