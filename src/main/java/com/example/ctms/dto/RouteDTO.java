package com.example.ctms.dto;

import java.util.List;

public record RouteDTO(
        Integer id,
        String name,
        Integer estimatedTime,
        Double distance,
        String status,
        String description,
        List<WaypointDTO> waypoints) {
}
