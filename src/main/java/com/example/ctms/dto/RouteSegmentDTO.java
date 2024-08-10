package com.example.ctms.dto;

public record RouteSegmentDTO(
        Integer id,
        Integer startWaypointId,
        Integer endWaypointId,
        Integer segmentOrder) {
}
