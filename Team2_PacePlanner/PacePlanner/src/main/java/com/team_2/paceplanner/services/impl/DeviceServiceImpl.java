package com.team_2.paceplanner.services.impl;

import com.team_2.paceplanner.dtos.DeviceStatusDTO;
import com.team_2.paceplanner.entities.DeviceStatus;
import com.team_2.paceplanner.enums.DeviceConnectionStatus;
import com.team_2.paceplanner.repositories.DeviceStatusRepository;
import com.team_2.paceplanner.services.DeviceService;
import com.team_2.paceplanner.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DeviceServiceImpl implements DeviceService {

    private final DeviceStatusRepository repository;
    private final NotificationService notificationService;

    @Autowired
    public DeviceServiceImpl(DeviceStatusRepository repository, NotificationService notificationService) {
        this.repository = repository;
        this.notificationService = notificationService;
    }

    @Override
    public void updateStatus(DeviceStatusDTO dto) {
        DeviceStatus status = new DeviceStatus();
        status.setUserId(dto.userId);
        status.setDeviceId(dto.deviceId);
        status.setStatus(DeviceConnectionStatus.valueOf(dto.status));
        status.setBatteryLevel(dto.batteryLevel);
        status.setLastSynced(LocalDateTime.now());

        repository.save(status);

        if (status.getBatteryLevel() < 20) {
            notificationService.sendBatteryLowAlert(dto.userId);
        }
        if (status.getStatus() == DeviceConnectionStatus.DISCONNECTED) {
            notificationService.sendDisconnectedAlert(dto.userId);
        }
    }

    @Override
    @Scheduled(fixedRate = 300000) // 5 minutes in milliseconds
    public void checkInactiveDevices() {
        LocalDateTime now = LocalDateTime.now();
        for (DeviceStatus status : repository.findAll()) {
            if (status.getLastSynced().isBefore(now.minusMinutes(10))) {
                notificationService.sendDisconnectedAlert(status.getUserId());
            }
        }
    }
}
