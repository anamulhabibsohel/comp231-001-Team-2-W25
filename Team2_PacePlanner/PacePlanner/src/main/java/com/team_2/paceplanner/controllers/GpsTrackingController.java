package com.team_2.paceplanner.controllers;

import com.team_2.paceplanner.entities.GpsTracking;
import com.team_2.paceplanner.services.GpsTrackingService;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/gps")
public class GpsTrackingController {
    private final GpsTrackingService service;

    public GpsTrackingController(GpsTrackingService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<GpsTracking> logGpsData(@RequestBody GpsTracking gpsTracking) {
        return ResponseEntity.ok(service.saveGpsData(gpsTracking));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<GpsTracking>> getUserGpsData(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getRecentGpsData(userId));
    }

    @GetMapping("/live/{userId}")
    public ResponseEntity<?> getLiveGpsData(@PathVariable Long userId) {
        Optional<JSONObject> gpsData = service.fetchLiveGpsData(userId);

        return gpsData.<ResponseEntity<?>>map(jsonObject -> ResponseEntity.ok(jsonObject.toString())).orElseGet(() -> ResponseEntity.status(500).body("{\"error\": \"Failed to fetch live GPS data\"}"));
    }

}
