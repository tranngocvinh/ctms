package com.example.ctms.dto;

public record RouteSegmentDTO(
        Integer startWaypointId,
        Integer endWaypointId,
        Integer segmentOrder) {
}
