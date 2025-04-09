package com.team_2.paceplanner.services;

public interface NotificationService {
    void sendBatteryLowAlert(Long userId);

    void sendDisconnectedAlert(Long userId);
}
