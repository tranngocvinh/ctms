package com.example.ctms.dto;

import java.time.LocalDateTime;

public record ScheduleSegmentDTO (
     Integer routeSegmentId,
     LocalDateTime departureTime,
     LocalDateTime arrivalTime

    // Constructors, Getters and Setters
){}
