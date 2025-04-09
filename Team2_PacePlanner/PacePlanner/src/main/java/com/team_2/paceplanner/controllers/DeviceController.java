package com.team_2.paceplanner.controllers;

import com.team_2.paceplanner.dtos.DeviceStatusDTO;
import com.team_2.paceplanner.services.DeviceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    private final DeviceService service;

    public DeviceController(DeviceService service) {
        this.service = service;
    }

    @PostMapping("/status")
    public ResponseEntity<?> updateStatus(@RequestBody DeviceStatusDTO dto) {
        service.updateStatus(dto);
        return ResponseEntity.ok("Status updated");
    }
}
