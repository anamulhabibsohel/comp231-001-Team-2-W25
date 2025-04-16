package com.team_2.paceplanner.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeightRecordDTO {
    public Long userId;
    public LocalDate date;
    public double weight;
    public String notes;
}
