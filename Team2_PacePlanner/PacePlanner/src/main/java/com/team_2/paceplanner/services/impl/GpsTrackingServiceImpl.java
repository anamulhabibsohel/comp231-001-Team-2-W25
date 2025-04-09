package com.team_2.paceplanner.services.impl;

import com.team_2.paceplanner.entities.GpsTracking;
import com.team_2.paceplanner.repositories.GpsTrackingRepository;
import com.team_2.paceplanner.services.GpsTrackingService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GpsTrackingServiceImpl implements GpsTrackingService {

    private final GpsTrackingRepository repository;

    @Value("${ipgeolocation.api.key}")
    private String apiKey;

    @Autowired
    public GpsTrackingServiceImpl(GpsTrackingRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public GpsTracking saveGpsData(GpsTracking gpsTracking) {
        return repository.save(gpsTracking);
    }

    @Override
    public List<GpsTracking> getRecentGpsData(Long userId) {
        return repository.findRecentGpsData(userId);
    }

    @Override
    public Optional<JSONObject> fetchLiveGpsData(Long userId) {
        try {
            String apiUrl = "https://api.ipgeolocation.io/ipgeo?apiKey=" + apiKey;
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                System.err.println("Error: Received response code " + responseCode);
                return Optional.empty();
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JSONObject fullResponse = new JSONObject(response.toString());

            // Extract relevant fields
            double latitude = fullResponse.getDouble("latitude");
            double longitude = fullResponse.getDouble("longitude");
            String city = fullResponse.getString("city");
            String country = fullResponse.getString("country_name");
            String countryEmoji = fullResponse.getString("country_emoji");

            // Save to database
            GpsTracking gpsTracking = new GpsTracking();
            gpsTracking.setUserId(userId);
            gpsTracking.setLatitude(latitude);
            gpsTracking.setLongitude(longitude);
            gpsTracking.setAltitude(0.0); // Default altitude
            gpsTracking.setRecordedAt(LocalDateTime.now());

            repository.save(gpsTracking);

            // Return simplified JSON response
            JSONObject simplifiedResponse = new JSONObject();
            simplifiedResponse.put("latitude", latitude);
            simplifiedResponse.put("longitude", longitude);
            simplifiedResponse.put("city", city);
            simplifiedResponse.put("country", country);
            simplifiedResponse.put("country_emoji", countryEmoji);

            return Optional.of(simplifiedResponse);

        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}