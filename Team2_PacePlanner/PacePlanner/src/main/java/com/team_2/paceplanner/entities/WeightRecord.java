package com.team_2.paceplanner.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Entity representing a user's weight record.
 * Stores weight measurements with corresponding dates.
 */
@Entity
@Table(name = "weight_records")
@Getter
@Setter
@NoArgsConstructor
public class WeightRecord {

    /**
     * Unique identifier for the weight record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Reference to the user this weight record belongs to.
     */
    @Column(nullable = false)
    @NotNull
    private Long userId;

    /**
     * Date when the weight was recorded.
     */
    @Column(nullable = false)
    @NotNull
    private LocalDate date;

    /**
     * Weight measurement in kilograms.
     */
    @Column(nullable = false)
    @Min(0)
    private double weight;
}