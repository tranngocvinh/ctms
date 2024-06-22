package com.example.ctms.repository;

import com.example.ctms.entity.PortLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PortLocationRepository extends JpaRepository<PortLocation, Integer> {
    List<PortLocation> findByPortNameContainingIgnoreCase(String portName);
}
