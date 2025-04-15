package com.team_2.paceplanner.services.impl;

import com.team_2.paceplanner.dtos.DeviceStatusDTO;
import com.team_2.paceplanner.entities.DeviceStatus;
import com.team_2.paceplanner.enums.DeviceConnectionStatus;
import com.team_2.paceplanner.repositories.DeviceStatusRepository;
import com.team_2.paceplanner.services.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeviceServiceImplTest {

    private DeviceStatusRepository repository;
    private NotificationService notificationService;
    private DeviceServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = mock(DeviceStatusRepository.class);
        notificationService = mock(NotificationService.class);
        service = new DeviceServiceImpl(repository, notificationService);
    }

    @Test
    @DisplayName("updateStatus should save valid device status and send no notifications for normal conditions")
    void updateStatusShouldSaveValidDeviceStatusAndSendNoNotifications() {
        DeviceStatusDTO dto = new DeviceStatusDTO(1L, "device123", "CONNECTED", 50);
        service.updateStatus(dto);

        ArgumentCaptor<DeviceStatus> captor = ArgumentCaptor.forClass(DeviceStatus.class);
        verify(repository).save(captor.capture());
        verifyNoInteractions(notificationService);

        DeviceStatus savedStatus = captor.getValue();
        assertEquals(dto.userId, savedStatus.getUserId());
        assertEquals(dto.deviceId, savedStatus.getDeviceId());
        assertEquals(DeviceConnectionStatus.CONNECTED, savedStatus.getStatus());
        assertEquals(dto.batteryLevel, savedStatus.getBatteryLevel());
        assertNotNull(savedStatus.getLastSynced());
    }

    @Test
    @DisplayName("updateStatus should send battery low alert when battery level is below threshold")
    void updateStatusShouldSendBatteryLowAlertWhenBatteryLevelIsBelowThreshold() {
        DeviceStatusDTO dto = new DeviceStatusDTO(1L, "device123", "CONNECTED", 15);
        service.updateStatus(dto);

        verify(notificationService).sendBatteryLowAlert(dto.userId);
    }

    @Test
    @DisplayName("updateStatus should send disconnected alert when device status is DISCONNECTED")
    void updateStatusShouldSendDisconnectedAlertWhenDeviceStatusIsDisconnected() {
        DeviceStatusDTO dto = new DeviceStatusDTO(1L, "device123", "DISCONNECTED", 50);
        service.updateStatus(dto);

        verify(notificationService).sendDisconnectedAlert(dto.userId);
    }

    @Test
    @DisplayName("checkInactiveDevices should send disconnected alert for devices inactive for more than 10 minutes")
    void checkInactiveDevicesShouldSendDisconnectedAlertForInactiveDevices() {
        DeviceStatus inactiveDevice = new DeviceStatus();
        inactiveDevice.setUserId(1L);
        inactiveDevice.setDeviceId("device123");
        inactiveDevice.setStatus(DeviceConnectionStatus.CONNECTED);
        inactiveDevice.setBatteryLevel(50);
        inactiveDevice.setLastSynced(LocalDateTime.now().minusMinutes(15));
        when(repository.findAll()).thenReturn(List.of(inactiveDevice));

        service.checkInactiveDevices();

        verify(notificationService).sendDisconnectedAlert(inactiveDevice.getUserId());
    }

    @Test
    @DisplayName("checkInactiveDevices should not send alerts for devices active within 10 minutes")
    void checkInactiveDevicesShouldNotSendAlertsForActiveDevices() {
        DeviceStatus activeDevice = new DeviceStatus();
        activeDevice.setUserId(1L);
        activeDevice.setDeviceId("device123");
        activeDevice.setStatus(DeviceConnectionStatus.CONNECTED);
        activeDevice.setBatteryLevel(50);
        activeDevice.setLastSynced(LocalDateTime.now().minusMinutes(5));
        when(repository.findAll()).thenReturn(List.of(activeDevice));

        service.checkInactiveDevices();

        verifyNoInteractions(notificationService);
    }
}