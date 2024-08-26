package com.ctms.dto;

import java.time.LocalDateTime;

public record ScheduleSegmentDTO (
     Integer routeSegmentId,
     LocalDateTime departureTime,
     LocalDateTime arrivalTime,
     Integer shipId
    // Constructors, Getters and Setters
){}
