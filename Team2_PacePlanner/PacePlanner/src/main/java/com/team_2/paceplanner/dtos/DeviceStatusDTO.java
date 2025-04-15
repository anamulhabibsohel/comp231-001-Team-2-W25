package com.team_2.paceplanner.dtos;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class DeviceStatusDTO {
    public Long userId;
    public String deviceId;
    public String status;
    public int batteryLevel;
}
