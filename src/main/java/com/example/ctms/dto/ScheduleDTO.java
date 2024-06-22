package com.example.ctms.dto;

import java.time.LocalDateTime;

public record ScheduleDTO(
        Integer id,
        Integer routeId,
        String routeName,
        LocalDateTime departureTime,
        LocalDateTime estimatedArrivalTime,
        LocalDateTime actualDepartureTime,
        LocalDateTime actualArrivalTime,
        String status,
        String notes
) {}
