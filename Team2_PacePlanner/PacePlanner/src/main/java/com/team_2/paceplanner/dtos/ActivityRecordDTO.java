package com.team_2.paceplanner.dtos;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
public class ActivityRecordDTO {
    public Long userId;
    public int steps;
    public double distance;
    public LocalDateTime timestamp;
}
