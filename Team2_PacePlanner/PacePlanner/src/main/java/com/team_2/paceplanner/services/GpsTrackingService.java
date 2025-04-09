package com.team_2.paceplanner.services;


import com.team_2.paceplanner.entities.GpsTracking;
import org.json.JSONObject;

import java.util.List;
import java.util.Optional;

public interface GpsTrackingService {
    GpsTracking saveGpsData(GpsTracking gpsTracking);
    List<GpsTracking> getRecentGpsData(Long userId);
    Optional<JSONObject> fetchLiveGpsData(Long userId);
}
