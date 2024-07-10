package com.example.ctms.repository;

import com.example.ctms.entity.PortLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PortLocationRepository extends JpaRepository<PortLocation, Integer> {
    List<PortLocation> findByPortNameContainingIgnoreCase(String portName);

    Optional<PortLocation> findByPortName(String portName);
}
