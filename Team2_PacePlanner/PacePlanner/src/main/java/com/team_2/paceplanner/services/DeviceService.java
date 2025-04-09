package com.team_2.paceplanner.services;

import com.team_2.paceplanner.dtos.DeviceStatusDTO;

public interface DeviceService {
    void updateStatus(DeviceStatusDTO dto);

    void checkInactiveDevices();
}
