package com.ctms.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ScheduleDTO(
        Integer id,
        Integer routeId,
        String routeName,
        LocalDateTime departureTime,
        LocalDateTime estimatedArrivalTime,
        String codeSchedule,
        List<WaypointDTO> waypoints,
        List<String> containerCodes,
        List<Integer> ships,
        List<ScheduleSegmentDTO> scheduleSegments) {
}
