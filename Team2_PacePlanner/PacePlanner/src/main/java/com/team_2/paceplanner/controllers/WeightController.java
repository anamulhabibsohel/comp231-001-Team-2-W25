package com.team_2.paceplanner.controllers;

import com.team_2.paceplanner.dtos.WeightRecordDTO;
import com.team_2.paceplanner.services.WeightService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weights")
public class WeightController {

    private final WeightService service;

    public WeightController(WeightService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> logWeight(@RequestBody WeightRecordDTO dto) {
        service.saveWeight(dto);
        return ResponseEntity.ok("Weight logged");
    }
}
