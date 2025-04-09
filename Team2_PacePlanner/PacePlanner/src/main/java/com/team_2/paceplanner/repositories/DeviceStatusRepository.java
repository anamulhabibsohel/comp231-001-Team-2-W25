package com.team_2.paceplanner.repositories;

import com.team_2.paceplanner.entities.DeviceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceStatusRepository extends JpaRepository<DeviceStatus, Long> {
    List<DeviceStatus> findAll();
}