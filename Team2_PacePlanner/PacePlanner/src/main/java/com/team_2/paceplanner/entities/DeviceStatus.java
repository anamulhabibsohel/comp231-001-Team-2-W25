package com.team_2.paceplanner.entities;

import com.team_2.paceplanner.enums.DeviceConnectionStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "device_status")
public class DeviceStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String deviceId;

    @Enumerated(EnumType.STRING)
    private DeviceConnectionStatus status;

    private int batteryLevel;
    private LocalDateTime lastSynced;
}
