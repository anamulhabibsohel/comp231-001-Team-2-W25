package com.team_2.paceplanner.dtos;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
public class WeightRecordDTO {
    public Long userId;
    public LocalDate date;
    public double weight;
}
