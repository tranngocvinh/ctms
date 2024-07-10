package com.example.ctms.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ScheduleDTO(
        Integer id,
        Integer routeId,
        String routeName,
        LocalDateTime departureTime,
        LocalDateTime estimatedArrivalTime,
        LocalDateTime actualDepartureTime,
        LocalDateTime actualArrivalTime,
        String status,
        String notes,
        List<WaypointDTO> waypoints,
        List<String> containerCodes,
        List<Integer> ships
) {
    public ScheduleDTO {
        ships = (ships == null) ? List.of() : ships;
    }
}
