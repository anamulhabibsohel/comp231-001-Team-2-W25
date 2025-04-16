package com.team_2.paceplanner.enums;

/**
 * Represents possible connection states of a tracking device.
 * Used for monitoring device status in the GPS tracking system.
 */
public enum DeviceConnectionStatus {
    /**
     * Device is successfully connected and operational
     */
    CONNECTED,

    /**
     * Device is not connected or has lost connection
     */
    DISCONNECTED,

    /**
     * Device battery level is critically low
     */
    LOW_BATTERY,

    /**
     * Device is not receiving power while connected
     */
    NOT_CHARGING
}