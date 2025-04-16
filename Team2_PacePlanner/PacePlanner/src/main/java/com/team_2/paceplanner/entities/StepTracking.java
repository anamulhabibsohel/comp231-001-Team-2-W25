package com.team_2.paceplanner.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "step_tracking")
public class StepTracking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    @Min(0)
    private int steps;

    @Column(nullable = false)
    private LocalDate trackingDate;

    @Column(length = 500)
    private String notes;

    private LocalDateTime createdAt = LocalDateTime.now();
}