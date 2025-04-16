package com.team_2.paceplanner.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Entity representing GPS tracking data for user activities.
 * Stores location coordinates, activity details, and route information.
 */
@Entity
@Table(name = "gps_tracking")
@Getter
@Setter
@NoArgsConstructor
public class GpsTracking {

    /**
     * Unique identifier for the GPS tracking record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Reference to the user who recorded the activity.
     * Must be a positive number.
     */
    @Column(nullable = false)
    @Min(1)
    private Long userId;

    /**
     * Latitude coordinate of the recorded position.
     */
    @Column(nullable = false)
    private double latitude;

    /**
     * Longitude coordinate of the recorded position.
     */
    @Column(nullable = false)
    private double longitude;

    /**
     * Altitude in meters above sea level.
     */
    @Column(nullable = false)
    private double altitude;

    /**
     * Type of activity (e.g., running, cycling, hiking).
     */
    private String activityType;

    /**
     * Total distance covered in kilometers.
     */
    private Double distance;

    /**
     * Duration of activity in hh:mm:ss format.
     */
    private String duration;

    /**
     * Average pace in mm:ss per kilometer format.
     */
    private String pace;

    /**
     * Total elevation gain in meters.
     */
    private Double elevationGain;

    /**
     * User-defined name for the activity.
     */
    private String activityName;

    /**
     * Additional notes or comments about the activity.
     */
    @Column(columnDefinition = "TEXT")
    private String notes;

    /**
     * Serialized route data containing waypoints and timestamps.
     */
    @Column(columnDefinition = "TEXT")
    private String routeData;

    /**
     * Timestamp when the GPS data was recorded.
     * Automatically set to current time when record is created.
     */
    private LocalDateTime recordedAt = LocalDateTime.now();
}