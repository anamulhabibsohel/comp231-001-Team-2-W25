package com.team_2.paceplanner.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "gps_tracking")
@Getter
@Setter
@NoArgsConstructor
public class GpsTracking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Min(1)
    private Long userId;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @Column(nullable = false)
    private double altitude;

    private String activityType;

    private Double distance; // In kilometers

    private String duration; // Stored as hh:mm:ss string

    private String pace; // Stored as mm:ss string

    private Double elevationGain;

    private String activityName;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(columnDefinition = "TEXT")
    private String routeData;

    private LocalDateTime recordedAt = LocalDateTime.now();
}
