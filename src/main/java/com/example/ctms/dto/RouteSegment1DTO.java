package com.example.ctms.dto;

import com.example.ctms.entity.Route;
import com.example.ctms.entity.Waypoint;

public record RouteSegment1DTO(
        Integer id,
        Waypoint startWaypoint,
        Waypoint endWaypoint,
        Route route,
        Integer segmentOrder
) {
}
