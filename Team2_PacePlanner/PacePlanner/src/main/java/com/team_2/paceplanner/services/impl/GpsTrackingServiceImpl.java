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

/**
 * Service implementation for managing GPS tracking records.
 * Provides functionality for saving GPS data and fetching live location information
 * using the ipgeolocation.io API.
 */
@Service
public class GpsTrackingServiceImpl implements GpsTrackingService {

    private final GpsTrackingRepository repository;

    @Value("${ipgeolocation.api.key}")
    private String apiKey;

    /**
     * Constructs a new GpsTrackingServiceImpl.
     *
     * @param repository the repository for GPS tracking operations
     */
    @Autowired
    public GpsTrackingServiceImpl(GpsTrackingRepository repository) {
        this.repository = repository;
    }

    /**
     * Saves GPS tracking data in a transaction.
     * Algorithm:
     * 1. Persists GPS record to database
     * 2. Returns saved entity with generated ID
     *
     * @param gpsTracking the GPS tracking record to save
     * @return the saved GPS tracking record
     */
    @Override
    @Transactional
    public GpsTracking saveGpsData(GpsTracking gpsTracking) {
        return repository.save(gpsTracking);
    }

    /**
     * Retrieves recent GPS tracking records for a user.
     * Algorithm:
     * 1. Queries repository for recent records by userId
     * 2. Returns list ordered by timestamp
     *
     * @param userId the user's ID
     * @return list of recent GPS tracking records
     */
    @Override
    public List<GpsTracking> getRecentGpsData(Long userId) {
        return repository.findRecentGpsData(userId);
    }

    /**
     * Fetches live GPS data from ipgeolocation.io API.
     * Algorithm:
     * 1. Builds API URL with authentication key
     * 2. Opens HTTP connection and sends GET request
     * 3. Validates response code (200 for success)
     * 4. Reads response stream into string buffer
     * 5. Parses JSON response and extracts:
     * - Latitude and longitude
     * - City and country names
     * - Country emoji flag
     * 6. Creates and saves GPS tracking record
     * 7. Builds simplified JSON response
     * 8. Returns Optional containing response or empty if error
     *
     * @param userId the user's ID
     * @return Optional containing location data as JSONObject
     */
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